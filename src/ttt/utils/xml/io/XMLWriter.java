/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import ttt.utils.xml.engine.interfaces.IXMLElement;

/**
 * Serve per scrivere un file XML tramite le classi messe a disposizione da
 * questa libreria.
 *
 * @author TTT
 */
public class XMLWriter {

    private final File f;

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
    public void writeDocument(XMLDocument document) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlsw = xmlof.createXMLStreamWriter(new FileOutputStream(f), "UTF-8");
            xmlsw.writeStartDocument("UTF-8", "1.0");
            document.getElements().forEach(el -> writeElement(xmlsw, el));
            xmlsw.flush();
            xmlsw.close();
        } catch (FileNotFoundException | XMLStreamException e) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeElement(XMLStreamWriter xmlsw, IXMLElement element) {
        try {
            xmlsw.writeStartElement(element.getName());
            element.getTags().forEach(tag -> {
                try {
                    xmlsw.writeAttribute(tag.getName(), tag.getValue());
                } catch (XMLStreamException ex) {
                    Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            if (element.getValue() != null) {
                xmlsw.writeCharacters(element.getValue());
            }
            xmlsw.flush();
            element.getElements().forEach(el -> writeElement(xmlsw, el));
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
