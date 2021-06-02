package ttt.utils.graph.dijkstra;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Node<G, T> {

    private final G value;
    private final HashMap<Node<G, T>, T> links;

    public Node(G value) {
        this.value = value;
        this.links = new HashMap<>();
    }

    /**
     * Ritorna il contenuto del nodo.
     * @return Contenuto del nodo.
     */
    public G getValue() {
        return value;
    }

    public Set<Node<G, T>> getLinks() {
        return links.keySet();
    }

    public void addNode(Node<G, T> node, T value) {
        links.put(node, value);
    }

    public void removeNode(Node<G, T> node) {
        links.remove(node);
    }

    /**
     * Ritorna la distanza dal nodo passato per parametro se presente,
     * altrimenti nullo.
     * @param node Nodo
     * @return Distanza
     */
    public T getLinkValue(Node<G, T> node) {
        return links.get(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return Objects.equals(value, node.value) && Objects.equals(links, node.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, links);
    }
}
