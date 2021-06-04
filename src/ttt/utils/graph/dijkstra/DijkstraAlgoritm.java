package ttt.utils.graph.dijkstra;

import java.util.*;

/**
 * Classe per la ricerca del percorso migliore dato il nodo di partenza.
 * (Minimum Spanning Tree)
 * <br>La distanza tra i nodi deve essere di tipo numerico.<br>
 * @param <G> Tipo di contenuto dei nodi.
 */
public class DijkstraAlgoritm <G extends DijkstraCalculable> {

    private static final double THRESHOLD = 0.00000001;

    private final Node<G, Double> first;

    private final HashMap<Node<G, Double>, Double> values;
    private final LinkedList<Node<G, Double>> to_connect;
    private final LinkedHashMap<Node<G, Double>, Node<G, Double>> previous;

    /**
     * Costruttore per la classe.
     * @param elements Lista dei nodi da collegare.
     * @param first Nodo iniziale da cui partire.
     */
    public DijkstraAlgoritm(List<Node<G, Double>> elements, Node<G, Double> first) {
        this.first = first;

        values = new HashMap<>();
        to_connect = (LinkedList<Node<G, Double>>) elements;
        previous = new LinkedHashMap<>();

        init();
    }

    private void init() {
        for (Node<G, Double> element : to_connect) {
            values.put(element, Double.MAX_VALUE);
            previous.put(element, null);
        }
        values.put(first, 0.0);
        to_connect.remove(first);
    }

    /**
     * Genera il percorso dei cammini minimi, a partire dal primo nodo passato nel costruttore.
     */
    public void findMinimumSpanningTree(){
        for (Node<G, Double> element : first.getLinks()) {
            previous.put(element, first);
            values.put(element, element.getValue().calculateDistance(first));
        }
        while (!to_connect.isEmpty()) {
            Node<G, Double> piu_vicino = null;
            double min = Double.MAX_VALUE;
            for (Node<G, Double> element : to_connect) {
                double dist = values.get(element);
                if (dist - min < THRESHOLD) {
                    min = dist;
                    piu_vicino = element;
                }
            }
            double dist = values.get(piu_vicino);
            for (Node<G, Double> element : piu_vicino.getLinks()) {
                double ricalc = dist + element.getValue().calculateDistance(piu_vicino);
                if (ricalc - values.get(element) < THRESHOLD) {
                    previous.put(element, piu_vicino);
                    values.put(element, ricalc);
                }
            }
            to_connect.remove(piu_vicino);
        }
    }

    /**
     * Ritorna la distanza minore possibile del nodo passato per parametro
     * dal primo nodo, da cui si fa partire la ricerca.
     * @param end_node Nodo finale.
     * @return Distanza tra nodo finale e nodo iniziale.
     */
    public double getDistanceFromStart(Node<G, Double> end_node){
        return values.get(end_node);
    }

    /**
     * Ritorna il percorso dal nodo di partenza a quello passato per parametro.
     * @param end_node Nodo di arrivo.
     * @return Lista dei nodi per arrivare al nodo finale.
     */
    public ArrayList<Node<G, Double>> getTrackFromStart(Node<G, Double> end_node){
        ArrayList<Node<G, Double>> to_ret = new ArrayList<>();
        to_ret.add(end_node);
        Node<G, Double> actual = previous.get(end_node);
        while(actual != null){
            to_ret.add(actual);
            actual = previous.get(actual);
        }
        Collections.reverse(to_ret);
        return to_ret;
    }


}
