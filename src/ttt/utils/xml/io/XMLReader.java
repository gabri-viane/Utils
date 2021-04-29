/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.io;

import java.io.File;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.document.XMLElement;
import ttt.utils.xml.document.XMLTag;

/**
 * Serve per leggere un file di testo strutturato come XML.
 *
 * @author TTT
 */
public class XMLReader {

    /*
    Quando si legge un file si deve ritornare un'istanza di una classe 
    Documento (da creare).
    Per ogni tag aperta si va a creare un nuovo elemento (classe Elemento - da creare) 
    che viene completato con i dati man mano letti, e per ogni tag chiusa si va 
    a salvare quest'ultimo nel penultimo elemento aperto (Se viene chiuso il primo
    tag allora questo elemento viene salvato nella root del documento).
    
    Ogni sezione del documento letto (perciò ad ogni evento) viene chiamata la 
    classe XMLEngine che si deve occupare di completare (assegnare i valori, 
    perciò chiamando metodi o direttamente variabili) con i valori letti.
     */
    private final File f;

    public XMLReader(File file) {
        this.f = file;
    }

    /**
     * Legge il file associato a questo {@link XMLReader} per estrapolarne un
     * oggetto {@link XMLDocument} con i relativi {@link XMLElement} ognungo
     * completo di tags e valori.
     *
     * @return Il nuovo documento tradotto in oggetti {@link XMLElement}
     * @throws IOException
     */
    public XMLDocument readDocument() throws IOException {
        if (f != null && f.exists() && f.isFile()) {
            XMLDocument document = new XMLDocument(f);
            try {
                XMLInputFactory xmlif = XMLInputFactory.newInstance();
                XMLStreamReader xmlsr = xmlif.createXMLStreamReader(f.getAbsolutePath(), new FileInputStream(f));
                parseDocument(xmlsr, document);
                xmlsr.close();
            } catch (FileNotFoundException | XMLStreamException e) {
                throw new IOException("Il file specificato non esiste o non è formattato correttamente.");
            }

            return document;
        } else {
            throw new IOException("Il file specificato non esiste o non è correttto.");
        }
    }

    /**
     * Esegue il parse per eventi base XML.
     *
     * @param xmlsr Lo stream di lettura XML.
     * @param d Il documento in cui vanno salvati gli elementi.
     */
    private void parseDocument(XMLStreamReader xmlsr, XMLDocument d) {
        try {
            while (xmlsr.hasNext()) {
                switch (xmlsr.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        d.getLast().addSubElement(parseElement(xmlsr));
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        d.getLast().close();
                        break;
                    case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento: stampa il testo
                        if (xmlsr.getText().trim().length() > 0) {
                            d.getLast().setValue(xmlsr.getText());
                        }
                        break;
                    default:
                        break;
                }
                xmlsr.next();
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Esegue il parse di un singolo elemento: lo crea e gli associa gli
     * attributi.
     *
     * @param xmlsr Lo stream di lettura XML.
     * @return Il nuovo oggetto {@link XMLElement}.
     */
    private XMLElement parseElement(XMLStreamReader xmlsr) {
        XMLElement element = new XMLElement(xmlsr.getLocalName());
        for (int i = 0; i < xmlsr.getAttributeCount(); i++) {
            XMLTag xmlTag = new XMLTag(xmlsr.getAttributeLocalName(i));
            xmlTag.setValue(xmlsr.getAttributeValue(i));
            element.addTag(xmlTag);
        }
        return element;
    }

}
