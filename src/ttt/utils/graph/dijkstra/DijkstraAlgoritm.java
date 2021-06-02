package ttt.utils.graph.dijkstra;

import java.util.*;

public class DijkstraAlgoritm <G extends DijkstraCalculable> {

    private static final double THRESHOLD = 0.00000001;

    private final Node<G, Double> first;
    private final Node<G, Double> last;

    private final HashMap<Node<G, Double>, Double> values;
    private final LinkedList<Node<G, Double>> to_connect;
    private final LinkedHashMap<Node<G, Double>, Node<G, Double>> previous;

    public DijkstraAlgoritm(List<Node<G, Double>> elements, Node<G, Double> first, Node<G, Double> last) {
        this.first = first;
        this.last = last;

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
        to_connect.remove(last);
    }

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

    public double getDistanceFromStart(Node<G, Double> end_node){
        return values.get(end_node);
    }

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
