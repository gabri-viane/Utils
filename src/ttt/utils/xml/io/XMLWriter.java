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
package ttt.utils.xml.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.engine.XMLWriteSupportEngine;
import ttt.utils.xml.engine.interfaces.IXMLElement;

/**
 * Serve per scrivere un file XML tramite le classi messe a disposizione da
 * questa libreria.
 *
 * @author TTT
 */
public class XMLWriter {

    private final File f;
    private XMLWriteSupportEngine changer;

    /**
     * Crea un nuovo XMLWriter associato ad un file, in cui si potrà salvare un
     * oggetto di tipo {@link XMLDocument} tramite il metodo messo a
     * disposizione: {@link XMLWriter#writeDocument(ttt.utils.xml.document.XMLDocument)
     * }.
     *
     * @param file Il file che conterrà il documento.
     */
    public XMLWriter(File file) {
        this.f = file;
    }

    /*
     Prende in input un'istanza di tipo XMLDocument e iterando in successione 
     i suoi elementi e li scrive man mano eseguendo il flush continuo (ad ogni 
     elemento processato).
     
    Deve permettere di creare un file vuoto nel caso non esiste, deve chiedere se
    si vuole sovrascrivere il file già esistente.
     */
    /**
     * Scrive nel file (lo crea se non esiste già, altrimenti lo sovrascrive) il
     * contenuto di un {@link XMLDocument}.<br>
     *
     * Per ogni {@link IXMLElement} contenuto nel documento viene eseguito il
     * flush subito dopo aver scritto l'apertura e i relativi tags.<br>
     *
     * Il file è scritto in codifica UTF-8 con formato XML 1.0
     *
     * @param document Il documento da salvare
     * @param hr Nel caso il valore sia {@code true} allora il file viene
     * stampato con formattazione Human Readable.
     */
    public void writeDocument(XMLDocument document, boolean hr) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            changer = new XMLWriteSupportEngine(document);
            changer.applyChanges();
            XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlsw = xmlof.createXMLStreamWriter(new FileOutputStream(f), "UTF-8");
            xmlsw.writeStartDocument("UTF-8", "1.0");
            if (hr) {
                xmlsw.writeCharacters("\n");
                document.getElements().forEach(el -> writeElementHR(xmlsw, el, ""));
            } else {
                document.getElements().forEach(el -> writeElement(xmlsw, el));
            }
            xmlsw.writeEndDocument();
            xmlsw.flush();
            xmlsw.close();
        } catch (FileNotFoundException | XMLStreamException e) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Si occupa della scrittura di un singolo elemento con i relativi tags, nel
     * caso l'elemento ne possieda a sua volta degli altri allora il metodo
     * procede a scrivere pure loro prima di chiudere l'elemento corrente.
     *
     * @param xmlsw Lo stream di scrittura del file.
     * @param element L'elemento da scrivere.
     */
    private void writeElement(XMLStreamWriter xmlsw, IXMLElement element) {
        try {
            xmlsw.writeStartElement(element.getName());
            element.getTags().forEach(tag -> {
                if (changer.doWriteTag(element, tag)) {
                    try {
                        xmlsw.writeAttribute(tag.getName(), tag.getValue());
                    } catch (XMLStreamException ex) {
                        Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            if (element.getValue() != null) {
                xmlsw.writeCharacters(element.getValue());
            }
            xmlsw.flush();
            if (XMLWriteSupportEngine.doWriteSubElements(element)) {
                element.getElements().forEach(el -> writeElement(xmlsw, el));
            }
            xmlsw.writeEndElement();
            xmlsw.flush();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * <b>HUMAN READABLE - STYLE</b><br>
     * Si occupa della scrittura di un singolo elemento con i relativi tags, nel
     * caso l'elemento ne possieda a sua volta degli altri allora il metodo
     * procede a scrivere pure loro prima di chiudere l'elemento corrente.
     *
     * @param xmlsw Lo stream di scrittura del file.
     * @param element L'elemento da scrivere.
     */
    private void writeElementHR(XMLStreamWriter xmlsw, IXMLElement element, String tabs) {
        try {
            xmlsw.writeCharacters(tabs);
            xmlsw.writeStartElement(element.getName());
            element.getTags().forEach(tag -> {
                if (changer.doWriteTag(element, tag)) {
                    try {
                        xmlsw.writeAttribute(tag.getName(), tag.getValue());
                    } catch (XMLStreamException ex) {
                        Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            if (element.getValue() != null) {
                xmlsw.writeCharacters(element.getValue());
            }
            xmlsw.flush();
            boolean bl = element.getElements().size() > 0;
            if (bl) {
                xmlsw.writeCharacters("\n");
                if (XMLWriteSupportEngine.doWriteSubElements(element)) {
                    element.getElements().forEach(el -> writeElementHR(xmlsw, el, tabs + "\t"));
                }
                xmlsw.writeCharacters(tabs);
            }
            xmlsw.writeEndElement();
            xmlsw.writeCharacters("\n");
            xmlsw.flush();
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
