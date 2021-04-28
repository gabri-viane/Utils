/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.EngineMethod;
import ttt.utils.xml.engine.annotations.Tag;
import ttt.utils.xml.engine.enums.MethodType;
import ttt.utils.xml.engine.interfaces.IXMLElement;
import ttt.utils.xml.engine.interfaces.IXMLTag;
import ttt.utils.xml.io.XMLReader;

/**
 * Si occupa di completare le classi che estendono le interfacce
 * {@link IXMLElement} oppure {@link IXMLTag}. Si basa inoltre sulle annotazioni
 * {@link Tag} e {@link Element} per poter caricare in modo autonomo i valori
 * tramite reflection.
 *
 * @author TTT
 */
public final class XMLEngine {

    private final XMLDocument document;
    private final Class<? extends XMLElement>[] classes;

    private final HashMap<String, Class<? extends XMLElement>> availableElements = new HashMap<>();

    public XMLEngine(File file, Class<? extends XMLElement>... classes) throws IOException {
        this(new XMLReader(file).readDocument(), classes);
    }

    public XMLEngine(XMLReader reader, Class<? extends XMLElement>... classes) throws IOException {
        this(reader.readDocument(), classes);
    }

    /**
     * Inizializza un nuovo XMLEngine per poter successivamente trasformare un
     * {@link XMLDocument} con {@link XMLElement} generici in uno con classi
     * specifiche.
     *
     * @param document Nuovo documento che userà le classi specificate
     * @param classes Classi che rappresentano gli elementi utilizzati nel file
     * xml.
     */
    public XMLEngine(XMLDocument document, Class<? extends XMLElement>... classes) {
        this.document = document;
        this.classes = classes;
        init();
    }

    private void init() {
        for (Class cls : classes) {
            Element elem_ann = getAnnotationFrom(cls);
            if (elem_ann != null) {
                availableElements.put(elem_ann.Name(), cls);
            }
        }
    }

    private Element getAnnotationFrom(Class c) {
        Annotation annotation = c.getAnnotation(Element.class);
        if (annotation != null) {
            Element elem_ann = (Element) annotation;
            return elem_ann;
        }
        return null;
    }

    public void morph(XMLDocument to) {
        document.getElements().forEach(to_transfer -> {
            try {
                Constructor constr = availableElements.get(to_transfer.getName()).getConstructor();
                Object newInstance = constr.newInstance();
                transfer(to_transfer, (IXMLElement) newInstance);
                to.addSubElement((IXMLElement) newInstance);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void transfer(IXMLElement from, IXMLElement to) {
        Class c = to.getClass();
        Element main_ann = getAnnotationFrom(c);
        ArrayList<Method> calc_methods = new ArrayList<>();
        for (Method m : c.getDeclaredMethods()) {
            EngineMethod meta = m.getAnnotation(EngineMethod.class);//Il metodo deve essre annotato con @EngineMethod
            if (meta != null) {
                if (meta.MethodType() == MethodType.SET) {
                    if (main_ann.CanHaveTags()) {
                        Tag tag_annot = m.getAnnotation(Tag.class);
                        if (tag_annot != null) {
                            if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == String.class) {
                                try {
                                    IXMLTag effective_tag = from.getTag(tag_annot.Name());
                                    m.invoke(to, effective_tag != null ? effective_tag.getValue() : null);
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                    Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                } else if (meta.MethodType() == MethodType.CALC && m.getParameterCount() == 0) {
                    calc_methods.add(m);
                }
            }
            //Completare per le variabili
            /*for (Field f : c.getDeclaredFields()) {

            }*/
        }
        from.getElements().forEach(to_transfer -> {
            try {
                Constructor constr = availableElements.get(to_transfer.getName()).getConstructor();
                Object newInstance = constr.newInstance();
                transfer(to_transfer, (IXMLElement) newInstance);
                to.addSubElement((IXMLElement) newInstance);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        calc_methods.forEach(method -> {
            try {
                method.invoke(to);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        if (main_ann.CanHaveValue()) {
            to.setValue(from.getValue());
        }
    }

}
