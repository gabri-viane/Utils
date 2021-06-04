package ttt.utils.graph.dijkstra;

import java.util.HashSet;

public class BinaryTree<K extends Comparable<K>, V extends Comparable<K>> {

    private final BinaryNode<K, V> root;
    private final HashSet<K> values;

    public BinaryTree(BinaryNode<K, V> first_node) {
        this.root = first_node;
        values = new HashSet<>();
        values.add(root.getValue());
    }

    /**
     * Ritorna il primo nodo dell'albero.
     * @return Primo nodo dell'albero.
     */
    public BinaryNode<K, V> getFirstNode() {
        return root;
    }

    /**
     * Aggiunge un nuovo nodo all'albero, se già presente un nodo con quel valore
     * non lo aggiunge.
     * @param current Nodo corrente su cui si sta effettuando l'operazione.
     * @param value Valore da aggiungere.
     */
    private void addNode(BinaryNode<K, V> current, K value){
        if (value.compareTo(current.getValue()) > 0) {  // se maggiore
            if(current.getRightSon() == null){ // se dx non è occupato lo mette li
                current.setRightSon(new BinaryNode<>(value));
            } else {
                addNode((BinaryNode<K, V>) current.getRightSon(), value);
            }
        } else if (value.compareTo(current.getValue()) < 0){ // se minore
            if (current.getLeftSon() == null) { // se sx non è occupato lo mette li
                current.setLeftSon(new BinaryNode<>(value));
            } else {
                addNode((BinaryNode<K, V>) current.getLeftSon(), value);
            }
        }
    }

    /**
     * Aggiunge un nuovo nodo all'albero, se già presente un nodo con quel valore
     * non lo aggiunge.
     * @param value Valore da aggiungere.
     */
    public void add (K value){
        addNode(root, value);
    }

    /**
     * Cerca un valore all'interno dell'albero e restituisce <code>true</code> se trovato,
     * altrimenti <false>;
     * @param current Nodo su cui si sta effettuando la ricerca.
     * @param value Valore cercato.
     * @return Se il valore è presente  o no.
     */
    private boolean isPresent(BinaryNode<K, V> current, K value){
        if(value.equals(current.getValue())){
            return true;
        } else if(current.getLeftSon() != null && value.compareTo(current.getValue()) < 0){
            return isPresent((BinaryNode<K, V>) current.getLeftSon(), value);
        } else if(current.getRightSon() != null){
            return isPresent((BinaryNode<K, V>) current.getRightSon(), value);
        } else {
            return false;
        }
    }

    /**
     * Ritorna se è presente o no il valore cercato.
     * @param value Valore cercato.
     * @return Se il valore è presente o no.
     */
    public boolean isPresent(K value){
        return isPresent(root, value);
    }

}
