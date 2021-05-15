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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ttt.utils.graph.exceptions.MeanlessException;

/**
 *
 * @author TTT
 * @param <T> Il tipo di nodo.
 * @param <K> Il tipo di link.
 */
public final class Node<T, K> {

    private final String name;
    private final HashMap<Node<T, K>, Link<T, K>> links = new HashMap<>();
    private T value;
    private Link cycle;

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public void setCycle(K link_value) {
        cycle = new Link(this, this);
        cycle.lock();
    }

    public void removeCycle() {
        cycle = null;
    }

    /**
     * Aggiunge un arco a questo nodo e ne associa il nodo opposto.
     *
     * @param l L'arco da aggiungere.
     */
    public void addLink(Link<T, K> l) {
        if (l != null && !links.containsValue(l)) {
            links.put(l.getLinked(this), l);
        }
    }

    /**
     * Rimuove un arco a questo nodo e ne associa il nodo opposto.
     *
     * @param l L'arco da rimuovere.
     */
    public void removeLink(Link<T, K> l) {
        if (l != null && links.containsValue(l)) {
            links.remove(l.getLinked(this));
            l.getLinked(this).removeLink(l);
        }
    }

    /**
     * Ritorna quanti archi non hanno ancora un valore.
     *
     * @return Il numero di archi non impostati.
     */
    public int countVoidLinks() {
        int to_ret = 0;
        to_ret = links.values().stream().filter(l -> (l.getFrom() == this && !l.isLocked())).map(l -> l.hasValue() ? 0 : 1).reduce(to_ret, Integer::sum);
        return to_ret;
    }

    /**
     * Ritorna il primo arco senza valore.
     *
     * @return L'arco se presente, altrimenti {@code null}.
     */
    public Optional<Link> getFirstVoidLink() {
        for (Link l : links.values()) {
            if (!l.hasValue() && !l.isLocked() && l.getFrom() == this) {
                return Optional.of(l);
            }
        }
        return Optional.empty();
    }

    /**
     * Ritorna il nome del nodo.
     *
     * @return Il nome.
     */
    public String getName() {
        return name;
    }

    /**
     * Ritorna il valore associato a questo nodo.
     *
     * @return Il valore precedentemente associato.
     */
    public T getValue() {
        return value;
    }

    /**
     * Ritorna gli archi che escono dal nodo.
     *
     * @return Lista di archi in uscita.
     * @throws MeanlessException Nel caso in cui l'arco sia bidirezionale.
     */
    public List<Link<T, K>> getOutputLinks() throws MeanlessException {
        ArrayList<Link<T, K>> to_ret = new ArrayList<>();
        links.values().stream().filter((l) -> {
            return l.getFrom() == this;
        }).forEach(l -> to_ret.add(l));
        return Collections.unmodifiableList(to_ret);
    }

    /**
     * Ritorna gli archi che entrano nel nodo.
     *
     * @return Lista di archi in ingresso.
     * @throws MeanlessException Nel caso in cui l'arco sia bidirezionale.
     */
    public List<Link<T, K>> getInputLinks() throws MeanlessException {
        ArrayList<Link<T, K>> to_ret = new ArrayList<>();
        links.values().stream().filter(l -> (l.getTo() == this))
                .forEachOrdered(l -> {
                    to_ret.add(l);
                });
        return Collections.unmodifiableList(to_ret);
    }

    /**
     * Ritorna il numero di archi in entrata senza valore.
     *
     * @return Il numero di archi.
     */
    public long countVoidInputLinks() {
        return getInputLinks().stream().filter(link -> {
            return !link.hasValue();
        }).count();
    }

    /**
     * Ritorna il numero di archi in uscita senza valore.
     *
     * @return Il numero di archi.
     * @throws MeanlessException Nel caso in cui l'arco sia bidirezionale.
     */
    public long countVoidOutputLinks() throws MeanlessException {
        return getOutputLinks().stream().filter(link -> {
            return !link.hasValue();
        }).count();
    }

    /**
     * Ritorna gli archi in entrata senza valore.
     *
     * @return Il numero di archi.
     * @throws MeanlessException Nel caso in cui l'arco sia bidirezionale.
     */
    public List<Link<T, K>> getVoidInputLinks() throws MeanlessException {
        ArrayList<Link<T, K>> to_ret = new ArrayList<>();
        getInputLinks().stream().filter(link -> {
            return !link.hasValue();
        }).forEach((t) -> {
            to_ret.add(t);
        });
        return Collections.unmodifiableList(to_ret);
    }

    /**
     * Ritorna gli archi in uscita senza valore.
     *
     * @return Il numero di archi.
     * @throws MeanlessException Nel caso in cui l'arco sia bidirezionale.
     */
    public List<Link<T, K>> getVoidOutputLinks() throws MeanlessException {
        ArrayList<Link<T, K>> to_ret = new ArrayList<>();
        getOutputLinks().stream().filter(link -> {
            return !link.hasValue();
        }).forEach((t) -> {
            to_ret.add(t);
        });
        return Collections.unmodifiableList(to_ret);
    }

    /**
     * Ritorna la lista chiave-valore di nodi e archi.
     *
     * @return Map non modificabile.
     */
    public Map<Node<T, K>, Link<T, K>> getAssociatedNodes() {
        return Collections.unmodifiableMap(links);
    }

    public Collection<Link<T, K>> getLinks() {
        return Collections.unmodifiableCollection(links.values());
    }

    /**
     * Restituisce l'arco per arrivare ad un altro nodo. Se il nodo d'arrivo è
     * uguale al nodo di partenza viene ritornato l'arco che rappresenta il
     * ciclo su se stesso.
     *
     * @param n Il nodo di arrivo.
     * @return L'arco che rappresenta il percorso da compiere.
     */
    public Link<T, K> to(Node<T, K> n) {
        return n == this ? cycle : links.get(n);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node[").append(name).append(", ").append(value).append("]");
        return sb.toString();
    }

}
