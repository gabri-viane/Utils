/*
 * Copyright 2021 TTT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
     * Controlla se ha un'elemento.
     *
     * @param element Elemento da trovare.
     * @return {@code true} se contiene l'elemento.
     */
    public boolean hasElement(IXMLElement element);

    /**
     * Aggiungi una tag.
     *
     * @param tag La {@link IXMLTag} da aggiungere
     */
    public void addTag(IXMLTag tag);

    /**
     * Rimuovi una tag.
     *
     * @param tag La {@link IXMLTag} da rimuovere
     */
    public void removeTag(IXMLTag tag);

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

    /**
     * Ritorna la lista di commenti presenti in quest'elemento.
     *
     * @return Lista di commenti.
     */
    public List<IXMLComment> getComments();

    /**
     * Aggiunge un commento all'elemento corrente.
     *
     * @param comment Il commento da aggiungere.
     */
    public void addComment(IXMLComment comment);
}
