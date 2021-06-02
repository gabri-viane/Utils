package ttt.utils.graph.dijkstra;

import ttt.utils.graph.exceptions.BinaryNodeException;

import java.util.HashSet;
import java.util.Set;

public class BinaryNode<K extends Comparable<K>, V extends Comparable<K> > extends Node<K, V> {

    private Node<K ,V> right_son;
    private Node<K ,V> left_son;

    public BinaryNode(K value) {
        super(value);
    }

    public BinaryNode(K value, Node<K, V> node1, Node<K, V> node2) {
        super(value);
         init(node1, node2);
    }

    private void init(Node<K, V> node1, Node<K, V> node2){
        int distance = node1.getValue().compareTo(node2.getValue());
        if(distance < 0) {
            left_son = node1;
            right_son = node2;
        } else if (distance > 0) {
            left_son = node2;
            right_son = node1;
        } else {
            throw new BinaryNodeException("I figli non possono avere lo stesso valore.");
        }
    }

    @Override
    public Set<Node<K, V>> getLinks() {
        HashSet<Node<K, V>> to_ret = new HashSet<>();
        to_ret.add(right_son);
        to_ret.add(left_son);
        return to_ret;
    }

    @Override
    public void addNode(Node<K, V> node, V value) {
        if(left_son == null){
            left_son = node;
        } else if(value.compareTo(left_son.getValue()) < 0){
            right_son = left_son;
            left_son = node;
        } else if(value.compareTo(left_son.getValue()) > 0){
            right_son = node;
        } else {
            throw new BinaryNodeException("I figli non possono avere lo stesso valore.");
        }
        super.addNode(node, value);
    }

    @Override
    public void removeNode(Node<K, V> node) {
        super.removeNode(node);
    }

    @Override
    public V getLinkValue(Node<K, V> node) {
        return super.getLinkValue(node);
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
