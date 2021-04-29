/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.interfaces;

import java.util.List;

/**
 * L'interfaccia che fornisce i metodi necessari per poter gestire una classe
 * come elemento istanziabile XML.
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
    public boolean hasSubElements();

    /**
     * Aggiunge all'elemento un sotto-elemento
     *
     * @param element
     */
    public void addSubElement(IXMLElement element);

    /**
     * Rimuovi da quest'elemento un sotto-elemento
     *
     * @param element
     */
    public void removeSubElement(IXMLElement element);

    /**
     * Controlla se ha un'elemento
     *
     * @param element
     * @return
     */
    public boolean hasElement(IXMLElement element);

    /**
     * Aggiungi una tag
     *
     * @param tag
     */
    public void addTag(IXMLTag tag);

    /**
     * Controlla se ha una tag.
     *
     * @param name
     * @return
     */
    public boolean hasTag(String name);

    /**
     * Restituisce una tag.
     *
     * @param name
     * @return
     */
    public IXMLTag getTag(String name);

    /**
     * Restituisce la lista di Tag associati a quest'elemento.
     *
     * @return
     */
    public List<IXMLTag> getTags();

    /**
     * Ritorna la lista di elementi
     *
     * @return
     */
    public List<IXMLElement> getElements();

    /**
     * Ritorna la prima occorrenza (se presente, altrimenti {@code null}) di
     * un'elemento con il nome specificato.
     *
     * @param name
     * @return
     */
    public IXMLElement getFirstElement(String name);

    /**
     * Ritrorna se l'elemento è stato chiuso.
     *
     * @return
     */
    public boolean isClosed();

    /**
     * Chiude l'elemento corrente e non modifica il comportamento del metodo {@link IXMLElement#getLast()
     * }.
     */
    public void close();

    /**
     * Ritorna l'ultimo elemento non chiuso che dipende da quest'elemento, o nel
     * caso tutti i sottoelementi siano chiusi ritorna se stesso, nel caso non
     * sia stato precedentemente chiuso. Altrimenti ritorna {@code null}.
     *
     * @return
     */
    public IXMLElement getLast();
}
