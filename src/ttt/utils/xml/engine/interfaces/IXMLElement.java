/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.interfaces;

import ttt.utils.xml.document.XMLTag;

/**
 *
 * @author TTT
 */
public interface IXMLElement {

    /*
    Deve definire tutti i metodi base per cui una classe può essere processata
    dalla classe XMLEngine come un'elemento (avere dei metodi di get e set che
    permettano la lettura e scrittura dei valori da file).
     */
    /**
     * Ritorna il nome dell'elemento.
     *
     * @return
     */
    public String getName();

    /**
     * Ritorna il valore dell'elemento se presente.
     *
     * @return
     */
    public String getValue();

    /**
     * Imposta il valore dell'elemento
     *
     * @param value
     */
    public void setValue(String value);

    /**
     * Ritorna se sono presenti dei sotto-elementi
     *
     * @return
     */
    public boolean hasSubElement();

    public void addSubElement(IXMLElement element);

    public void removeSubElement(IXMLElement element);

    public boolean hasElement(IXMLElement element);

    public void addTag(IXMLTag tag);

    public boolean hasTag(String name);

    public IXMLTag getTag(String name);

}
