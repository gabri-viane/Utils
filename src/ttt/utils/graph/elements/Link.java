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
package ttt.utils.graph.elements;

import java.util.Objects;
import ttt.utils.graph.exceptions.MeanlessException;
import ttt.utils.graph.exceptions.UnmodifiableException;

/**
 *
 * @author TTT
 * @param <K> Il tipo di valore.
 */
public class Link<K> {

    private K value = null;
    private Node from;
    private Node to;
    private boolean unidirectional = false;

    private boolean unlocked = true;

    public Link(Node from, Node to, boolean unidirectional) {
        this.from = from;
        this.to = to;
        this.unidirectional = unidirectional;
    }

    public Link(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Restituisce il nodo da cui l'arco parte.
     *
     * @return Il nodo associato.
     * @throws MeanlessException Nel caso l'arco sia bidirezionale.
     */
    public Node getFrom() throws MeanlessException {
        if (unidirectional) {
            return from;
        }
        throw new MeanlessException("L'arco è bidirezionale. Quest'operazione non è ammessa.");
    }

    /**
     * Restituisce il nodo a cui l'arco arriva.
     *
     * @return Il nodo associato.
     * @throws MeanlessException Nel caso l'arco sia bidirezionale.
     */
    public Node getTo() throws MeanlessException {
        if (unidirectional) {
            return to;
        }
        throw new MeanlessException("L'arco è bidirezionale. Quest'operazione non è ammessa.");
    }

    /**
     * Ritorna la Potenza d'Interazione associata a quest'arco.
     *
     * @return Il valore associato se è stato calcolato precedentemente,
     * altrimenti {@code null}.
     */
    public K getValue() {
        return value;
    }

    /**
     * Imposta il valore dell'arco tra i due nodi.Il valore può essere cambiato
     * solamente se l'arco non è bloccato.
     *
     * @param value Il valore dell'arco.
     * @throws ttt.utils.graph.exceptions.UnmodifiableException Viene lanciata
     * nel caso si provi a modificare un arco precedentemente bloccato.
     */
    public void setValue(K value) throws UnmodifiableException {
        if (unlocked) {
            this.value = value;
        }
        throw new UnmodifiableException("L'arco è bloccato. Il valore non può più essere modificato.");
    }

    /**
     * Controlla se l'arco è già stato inizializzato.
     *
     * @return Ritorna {@code true} se l'arco è già stato calcolato.
     */
    public boolean hasValue() {
        return value != null;
    }

    /**
     * Ritorna il nodo opposto.
     *
     * @param n Il nodo appartenente a questo arco.
     * @return Il nodo opposto a quello passato come parametro, altrimenti
     * {@code null} se il nodo passato non fa parte dell'arco.
     */
    public Node getLinked(Node n) {
        if (n != null && (n == from || n == to)) {
            return n == from ? to : from;
        }
        return null;
    }

    /**
     * Blocca l'arco da possibili cambiamenti di valore.
     */
    public void lock() {
        unlocked = false;
    }

    /**
     * Se l'elemento è bloccato il valore del potere non potrà essere cambiato.
     *
     * @return {@code true} se l'elemento è bloccato.
     */
    public boolean isLocked() {
        return !unlocked;
    }

    /**
     * Ritorna {@code true} il caso in cui l'arco è unidirezionale
     *
     * @return {@code true} se è unidirezionale, {@code false} nel caso in cui
     * sia bidirezionale.
     */
    public boolean isUnidirectional() {
        return unidirectional;
    }

    /**
     * Inverte la direzione dell'arco.
     *
     * @throws MeanlessException Nel caso l'arco sia bidirezionale.
     */
    public void changeDirection() throws MeanlessException {
        if (unidirectional) {
            Node tmp = this.to;
            this.to = this.from;
            this.from = tmp;
        }
        throw new MeanlessException("L'arco è bidirezionale. Quest'operazione non è ammessa.");
    }

    @Override
    public String toString() {
        String prev = (isUnidirectional() ? " =" : " <=");
        String after = "=> ";
        return "LNK: " + from.getName() + prev + (hasValue() ? value : "") + after + to.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            Link l = (Link) obj;
            return (l.from == this.from && l.to == this.to) || (l.from == this.to && l.to == this.from);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.from);
        hash = 71 * hash + Objects.hashCode(this.to);
        return hash;
    }
}
