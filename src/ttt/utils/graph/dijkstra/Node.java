package ttt.utils.graph.dijkstra;

import java.util.ArrayList;

public class Node<G> {

    private final G value;

    private final ArrayList<Node<G>> nodes;

    public Node(G value) {
        this.value = value;
        this.nodes = new ArrayList<>();
    }

    public G getValue(){
        return value;
    }

    public ArrayList<Node<G>> getNodes(){
        return nodes;
    }

    public void addNode(Node<G> node){
        nodes.add(node);
    }

    public void removeNode(Node<G> node){
        nodes.remove(node);
    }

}
