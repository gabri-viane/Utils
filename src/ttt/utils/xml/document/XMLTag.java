/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.document;

import ttt.utils.xml.engine.interfaces.IXMLTag;

/**
 * Tag base di un file XML. Contiene un valore rappresentato da un nome.
 *
 * @author TTT
 */
public class XMLTag implements IXMLTag {

    private String name;
    private String value;

    /**
     * Crea una nuova Tag, deve essere associato subito ad un nome.
     *
     * @param name Il nome dell'attributo.
     */
    public XMLTag(String name) {
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

}
