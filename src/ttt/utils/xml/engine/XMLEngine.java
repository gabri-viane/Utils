/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine;

import java.io.File;
import java.io.IOException;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.Tag;
import ttt.utils.xml.engine.interfaces.IXMLElement;
import ttt.utils.xml.engine.interfaces.IXMLTag;
import ttt.utils.xml.io.XMLReader;

/**
 * Si occupa di completare le classi che estendono le interfacce
 * {@link IXMLElement} oopure {@link IXMLTag}. Si basa inoltre sulle annotazioni
 * {@link Tag} e {@link Element} per poter caricare in modo autonomo i valori
 * tramite reflection.
 *
 * @author TTT
 */
public final class XMLEngine {

    private final XMLDocument document;

    public XMLEngine(File file) throws IOException {
        XMLReader reader = new XMLReader(file);
        document = reader.readDocument();
    }

    public XMLEngine(XMLReader reader) throws IOException {
        document = reader.readDocument();
    }

    public XMLEngine(XMLDocument document) {
        this.document = document;
    }

    public void complete(IXMLElement root) {
        
    }

}
