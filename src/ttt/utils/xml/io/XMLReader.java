/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.io;

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
}
