/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.interfaces;

/**
 *
 * @author TTT
 */
public interface IXMLTag {

    /*
     Indica una tag di un elemento XML che può essere più complessa
    <elemento tag=valore/>
    Bisogna definire i metodi base di get e set.
     */
    /**
     * Imposta il nome della Tag
     *
     * @param name
     */
    public void setName(String name);

    /**
     * Imposta il valore della Tag
     *
     * @param value
     */
    public void setValue(String value);

    /**
     * Ritorna il valore associato alla Tag
     *
     * @return
     */
    public String getValue();

    /**
     * Ritorna il nome della Tag.
     *
     * @return
     */
    public String getName();

}
