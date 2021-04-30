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
     * @return Il nome dell'elemento.
     */
    public String getName();

    /**
     * Ritorna il valore dell'elemento se presente.
     *
     * @return Il valore dell'elemento.
     */
    public String getValue();

    /**
     * Imposta il valore dell'elemento.
     *
     * @param value Il valore da impostare.
     */
    public void setValue(String value);

    /**
     * Ritorna se sono presenti dei sotto-elementi.
     *
     * @return {@code true} se contiene altri {@link IXMLElement}.
     */
    public boolean hasSubElements();

    /**
     * Aggiunge all'elemento un sotto-elemento.
     *
     * @param element L'elemento da aggiungere.
     */
    public void addSubElement(IXMLElement element);

    /**
     * Rimuovi da quest'elemento un sotto-elemento.
     *
     * @param element L'elemento da rimuovere.
     */
    public void removeSubElement(IXMLElement element);

    /**
     * Controlla se ha un'elemento
     *
     * @param element Elemento da trovare.
     * @return {@code true} se contiene l'elemento.
     */
    public boolean hasElement(IXMLElement element);

    /**
     * Aggiungi una tag
     *
     * @param tag La {@link IXMLTag} da aggiungere
     */
    public void addTag(IXMLTag tag);

    /**
     * Controlla se ha una tag.
     *
     * @param name Nome della tag da cercare.
     * @return {@code true} se contiene un'attributo con quel nome.
     */
    public boolean hasTag(String name);

    /**
     * Restituisce una tag.
     *
     * @param name Il nome dell'attributo da cercare.
     * @return La {@link IXMLTag} associata al nome cercato (se presente).
     */
    public IXMLTag getTag(String name);

    /**
     * Restituisce la lista di Tag associati a quest'elemento.
     *
     * @return Lista di attributi.
     */
    public List<IXMLTag> getTags();

    /**
     * Ritorna la lista di elementi contenuti nell'elemento corrente.
     *
     * @return Lista di elementi.
     */
    public List<IXMLElement> getElements();

    /**
     * Ritorna la prima occorrenza (se presente, altrimenti {@code null}) di
     * un'elemento con il nome specificato.
     *
     * @param name Il nome dell'elemento da cercare.
     * @return L'elemento trovato, altrimenti {@code null}.
     */
    public IXMLElement getFirstElement(String name);

    /**
     * Ritrorna se l'elemento è stato chiuso.
     *
     * @return {@code true} se è stato precedentemente chiuso.
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
     * @return L'ultimo elemento disponibile oppure {@code null}.
     */
    public IXMLElement getLast();
}
