/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.io;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

     /**
     * Metodo che legge tutte le persone dal file "inputPersone.xml" e ritorna un array-list di {@link Persona}
     * @return lista delle persone con dati personali
     * @throws FileNotFoundException e
     * @throws XMLStreamException e
     */
    public static ArrayList<Persona> leggiPersone() throws FileNotFoundException, XMLStreamException {

        ArrayList<Persona> lista_persone = new ArrayList<>();
        Persona p;
        String nome = "";
        String cognome = "";
        String  sesso = "";
        Data data_di_nascita = null;
        String luogo_di_nascita = "";

        FileInputStream file = new FileInputStream("inputPersone.xml");

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader("inputPersone.xml", file);
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }

        int contatore = 0;

        while (xmlr.hasNext()) {
            if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName().equals("persona")){

                contatore = 0;

                while (contatore < 5){
                    xmlr.next();
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName().equals("nome")){
                        xmlr.next();
                        nome = xmlr.getText();
                        contatore++;
                        xmlr.next();
                    }
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName().equals("cognome")){
                        xmlr.next();
                        cognome = xmlr.getText();
                        contatore++;
                        xmlr.next();
                    }
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName().equals("sesso")){
                        xmlr.next();
                        sesso = xmlr.getText();
                        contatore++;
                        xmlr.next();
                    }
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName().equals("comune_nascita")){
                        xmlr.next();
                        luogo_di_nascita = xmlr.getText();
                        contatore++;
                        xmlr.next();
                    }
                    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName().equals("data_nascita")){
                        xmlr.next();
                        data_di_nascita = new Data(xmlr.getText());
                        contatore++;
                        xmlr.next();
                    }
                }
                p = new Persona(nome, cognome, sesso, data_di_nascita, luogo_di_nascita);
                lista_persone.add(p);
            }
            xmlr.next();
        }
        return  lista_persone;
    }

    public static ArrayList<Comune> leggiComuni() throws FileNotFoundException, XMLStreamException {

        ArrayList<Comune> lista_comuni = new ArrayList<>();

        FileInputStream file = new FileInputStream("comuni.xml");

        XMLInputFactory xmlif = null;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader("inputPersone.xml", file);
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del reader:");
            System.out.println(e.getMessage());
        }

        while (xmlr.hasNext()){

            int contatore = 0;
            String nome = "";
            String codice = "";
            Comune comune_letto = null;

            if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName() == "comune"){
                while(contatore < 2){
                    xmlr.next();
                    if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName() == "nome"){
                        xmlr.next();
                        nome = xmlr.getText();
                        contatore++;
                    }
                    if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT && xmlr.getLocalName() == "codice"){
                        xmlr.next();
                        codice = xmlr.getText();
                        contatore++;
                    }
                }
                comune_letto = new Comune(nome, codice);
                lista_comuni.add(comune_letto);
            }
            xmlr.next();
        }
        return lista_comuni;
    }
}


}
