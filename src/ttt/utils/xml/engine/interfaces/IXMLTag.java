/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.interfaces;

/**
 * Interfaccia che deve essere implmentata dalle classi che rappresentano
 * un'attributo (un tag) di un elemento XML.
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
     * Imposta il nome della Tag.
     *
     * @param name Il nome dell'attributo.
     */
    public void setName(String name);

    /**
     * Imposta il valore della Tag
     *
     * @param value Il valore dell'attributo.
     */
    public void setValue(String value);

    /**
     * Ritorna il valore associato alla Tag
     *
     * @return Il nome dell'attributo.
     */
    public String getValue();

    /**
     * Ritorna il nome della Tag.
     *
     * @return Il valore dell'attributo.
     */
    public String getName();

}
