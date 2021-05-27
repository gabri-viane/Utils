/*
 * Copyright 2021 TTT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import ttt.utils.engines.enums.FieldType;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.engines.interfaces.EngineMethod;
import ttt.utils.xml.engine.annotations.Tag;
import ttt.utils.engines.enums.MethodType;
import ttt.utils.engines.interfaces.EngineField;
import ttt.utils.engines.utils.EngineUtils;
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

    /**
     * Uguale al comportamento di {@link XMLEngine#XMLEngine(ttt.utils.xml.document.XMLDocument, java.lang.Class...)
     * }.
     *
     * @param file Il file da leggere come documento XML.
     * @param classes Le classi che devono essere utilizzate per lo scambio
     * @throws IOException Nel caso in cui il file non esiste.
     */
    public XMLEngine(File file, Class<? extends XMLElement>... classes) throws IOException {
        this(new XMLReader(file).readDocument(), classes);
    }

    /**
     * Uguale al comportamento di {@link  XMLEngine#XMLEngine(ttt.utils.xml.document.XMLDocument, java.lang.Class...)
     * }.
     *
     * @param reader La classe Reader inizializzata per poter leggere il
     * documento.
     * @param classes Le classi che devono essere utilizzate per lo scambio.
     * @throws IOException Nel caso in cui il file non esiste
     */
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

    /**
     * Inizializza la lista di classi usate nel documento. Ogni classe deve
     * implementare l'interfaccia {@link IXMLElement} ed essre annotata con
     * {@link Element} per poter poi essere usata da questo engine
     */
    private void init() {
        for (Class cls : classes) {
            Element elem_ann = getAnnotationFrom(cls);
            if (elem_ann != null) {
                availableElements.put(elem_ann.Name(), cls);
            }
        }
    }

    /**
     * Restituisce l'annotazione, se presente, {@link Element} associata ad una
     * classe.
     *
     * @param c La classe da controllare.
     * @return L'annotazone {@link Element} associata alla classe.
     */
    protected static Element getAnnotationFrom(Class c) {
        Annotation annotation = c.getAnnotation(Element.class);
        if (annotation != null) {
            Element elem_ann = (Element) annotation;
            return elem_ann;
        }
        return null;
    }

    /**
     * Converte tutti gli {@link IXMLElement} possibili nelle istanze delle
     * classi specificate nel costruttore.<br>
     * Ogni elemento viene convertito in una classe specificata solo se la sua
     * annotazione {@link Element#Name() } contiene lo stesso nome
     * dell'elemento.<p>
     * Ad esempio:<br> {@code <esempio_elemento>valore</esempio_elemento>}<br>
     * Può essere associato solo ad una classe che implementa
     * {@link IXMLElement} ed è annotata così:<br>
     * {@code @Element(Name = "esempio_elemento")}
     *
     * @param to Documento in cui salvare la nuova struttura
     */
    public void morph(XMLDocument to) {
        document.getElements().forEach(to_transfer -> {
            try {
                Class<? extends XMLElement> to_instance = availableElements.get(to_transfer.getName());
                Object newInstance;
                if (to_instance != null) {
                    Constructor constr = to_instance.getConstructor();
                    newInstance = constr.newInstance();
                } else {
                    newInstance = new XMLElement(to_transfer.getName());
                }
                transfer(to_transfer, (IXMLElement) newInstance);
                to.addSubElement((IXMLElement) newInstance);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Trasferisce i valori di un generico {@link XMLElement} in uno nuovo, nel
     * caso rappresentato da una nuova classe.
     *
     * @param from L'elemento da trasferire, copiare.
     * @param to L'elemento di destinzaione (può essere una nuova classe).
     */
    private void transfer(IXMLElement from, IXMLElement to) {
        Class c = to.getClass();
        Element main_ann = getAnnotationFrom(c);
        ArrayList<Method> calc_methods = new ArrayList<>();
        if (main_ann != null) {
            if (main_ann.CanHaveValue()) {
                to.setValue(from.getValue());
            }
            for (Method m : c.getDeclaredMethods()) {
                EngineMethod meta = m.getAnnotation(EngineMethod.class);//Il metodo deve essre annotato con @EngineMethod
                if (meta != null) {
                    if (meta.MethodType() == MethodType.SET) {
                        if (main_ann.CanHaveTags()) {
                            Tag tag_annot = m.getAnnotation(Tag.class);
                            if (tag_annot != null && m.getParameterCount() == 1) {
                                Class<?> param1 = m.getParameterTypes()[0];
                                if (param1.equals(tag_annot.ValueType())) {
                                    IXMLTag effective_tag = from.getTag(tag_annot.Name());
                                    try {
                                        if (param1 == String.class) {
                                            m.invoke(to, effective_tag != null ? effective_tag.getValue() : null);
                                        } else if (EngineUtils.isPrimitive(param1) || EngineUtils.isBoxed(param1)) {
                                            m.invoke(to, effective_tag != null ? EngineUtils.convertStringToPrimitive(effective_tag.getValue(), param1) : null);
                                        }
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
            }
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                EngineField meta = f.getAnnotation(EngineField.class);
                if (meta != null && (meta.FieldType() == FieldType.READ_AND_WRITE || meta.FieldType() == FieldType.READ)) {
                    if (main_ann.CanHaveTags()) {
                        Tag tag_annot = f.getAnnotation(Tag.class);
                        if (tag_annot != null) {
                            try {
                                IXMLTag effective_tag = from.getTag(tag_annot.Name());
                                if (f.getType().equals(String.class)) {
                                    f.set(to, effective_tag != null ? effective_tag.getValue() : null);
                                } else if (EngineUtils.isPrimitive(f.getType()) || EngineUtils.isBoxed(f.getType())) {
                                    f.set(to, effective_tag != null ? EngineUtils.convertStringToPrimitive(effective_tag.getValue(), f.getType()) : null);
                                }
                            } catch (IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
        from.getTags().forEach(tag -> to.addTag(tag));
        from.getElements().forEach(to_transfer -> {
            try {
                Class<? extends XMLElement> to_instance = availableElements.get(to_transfer.getName());
                Object newInstance;
                if (to_instance != null) {
                    Constructor constr = to_instance.getConstructor();
                    newInstance = constr.newInstance();
                } else {
                    newInstance = new XMLElement(to_transfer.getName());
                }
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
    }
}
