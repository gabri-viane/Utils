package ttt.utils.graph.dijkstra;

import ttt.utils.graph.exceptions.BinaryNodeException;

import java.util.HashMap;
import java.util.Set;

public class BinaryNode<K extends Comparable<K>, V extends Comparable<K> > extends Node<K, V> {

    private Node<K ,V> right_son;
    private Node<K ,V> left_son;
    private final HashMap<Node<K, V>, V> sons;

    public BinaryNode(K value) {
        super(value);
        sons = new HashMap<>();
    }

    public BinaryNode(K value, Node<K, V> node1, Node<K, V> node2) {
        super(value);
        sons = new HashMap<>();
        init(node1, node2);
    }

    private void init(Node<K, V> node1, Node<K, V> node2){
        int distance = node1.getValue().compareTo(node2.getValue());
        if(distance < 0) {
            left_son = node1;
            right_son = node2;
            addSonsToMap();
        } else if (distance > 0) {
            left_son = node2;
            right_son = node1;
            addSonsToMap();
        } else {
            throw new BinaryNodeException("I figli non possono avere lo stesso valore.");
        }
    }

    /**
     * Aggiunge i figli alla mappa.
     */
    private void addSonsToMap(){
        sons.put(left_son, left_son.getLinkValue(this));
        sons.put(right_son, right_son.getLinkValue(this));
    }

    /**
     * Ritorna il figlio destro del nodo.
     * @return Figlio destro del nodo.
     */
    public Node<K, V> getRightSon() {
        return right_son;
    }

    /**
     * Ritorna il figlio sinistro del nodo.
     * @return Figlio sinistro del nodo.
     */
    public Node<K, V> getLeftSon() {
        return left_son;
    }

    /**
     * Imposta il figlio destro.
     * @param node Nodo da impostare.
     */
    public void setRightSon(Node<K, V> node){
        right_son = node;
    }

    /**
     * Imposta il figlio sinistro.
     * @param node Nodo da impostare.
     */
    public void setLeftSon(Node<K, V> node){
        left_son = node;
    }

    @Override
    public Set<Node<K, V>> getLinks() {
        return sons.keySet();

//        HashSet<Node<K, V>> to_ret = new HashSet<>();
//        to_ret.add(right_son);
//        to_ret.add(left_son);
//        return to_ret;
    }

    @Override
    public void addNode(Node<K, V> node, V value) {
//        if(left_son == null){
//            left_son = node;
//        } else if(value.compareTo(left_son.getValue()) < 0){
//            right_son = left_son;
//            left_son = node;
//        } else if(value.compareTo(left_son.getValue()) > 0){
//            right_son = node;
//        } else {
//            throw new BinaryNodeException("I figli non possono avere lo stesso valore.");
//        }
//        super.addNode(node, value);
    }

    @Override
    public void removeNode(Node<K, V> node) {
        if(left_son == node){
            left_son = null;
        } else if(right_son == node){
            right_son = null;
        }
    }

    @Override
    public V getLinkValue(Node<K, V> node) {
        if(left_son == node){
            return left_son.getLinkValue(this);
        } else if(right_son == node){
            return right_son.getLinkValue(this);
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
