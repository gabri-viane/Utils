/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttt.utils.xml.document.XMLDocument;
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

    public XMLEngine(File file) throws IOException {
        XMLReader reader = new XMLReader(file);
        document = reader.readDocument();
    }

    public XMLEngine(XMLReader reader) throws IOException {
        document = reader.readDocument();
    }

    public XMLEngine(XMLDocument document) {
        this.document = document;
    }

    public void transfer(IXMLElement from, IXMLElement to) {
        to.setValue(from.getValue());
        Class c = to.getClass();
        for (Method m : c.getDeclaredMethods()) {
            EngineMethod meta = m.getAnnotation(EngineMethod.class);//Il metodo deve essre annotato con @EngineMethod
            if (meta != null && meta.MethodType() == MethodType.SET) {
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
                    continue;//Se è annotata come Tag non può essere annotata anche come Element
                }
                Element tag_elem = m.getAnnotation(Element.class);
                if (tag_elem != null) {
                    if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == IXMLElement.class) {
                        try {
                            IXMLElement effective_element = from.getFirstElement(tag_elem.Name());
                            m.invoke(to, effective_element);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        //Completare per le variabili
        for (Field f : c.getDeclaredFields()) {

        }
    }

}
