package ttt.utils.graph.dijkstra;

import java.util.*;

public class DijkstraAlgoritm <G extends DijkstraCalculable> {

    private static final double THRESHOLD = 0.00000001;

    private final Node<G> first;
    private final Node<G> last;

    private final HashMap<Node<G>, Double> values;
    private final LinkedList<Node<G>> to_connect;
    private final LinkedHashMap<Node<G>, Node<G>> previous;

    public DijkstraAlgoritm(List<Node<G>> elements, Node<G> first, Node<G> last) {
        this.first = first;
        this.last = last;

        values = new HashMap<>();
        to_connect = (LinkedList<Node<G>>) elements;
        previous = new LinkedHashMap<>();

        init();
    }

    private void init() {
        for (Node<G> element : to_connect) {
            values.put(element, Double.MAX_VALUE);
            previous.put(element, null);
        }
        values.put(first, 0.0);
        to_connect.remove(last);
    }

    public void findMinimumSpanningTree(){
        for (Node<G> element : first.getNodes()) {
            previous.put(element, first);
            values.put(element, element.getValue().calculateDistance(first));
        }
        while (!to_connect.isEmpty()) {
            Node<G> piu_vicino = null;
            double min = Double.MAX_VALUE;
            for (Node<G> element : to_connect) {
                double dist = values.get(element);
                if (dist - min < THRESHOLD) {
                    min = dist;
                    piu_vicino = element;
                }
            }
            double dist = values.get(piu_vicino);
            for (Node<G> element : piu_vicino.getNodes()) {
                double ricalc = dist + element.getValue().calculateDistance(piu_vicino);
                if (ricalc - values.get(element) < THRESHOLD) {
                    previous.put(element, piu_vicino);
                    values.put(element, ricalc);
                }
            }
            to_connect.remove(piu_vicino);
        }
    }

    public double getDistanceFromStart(Node<G> end_node){
        return values.get(end_node);
    }

    public ArrayList<Node<G>> getTrackFromStart(Node<G> end_node){
        ArrayList<Node<G>> to_ret = new ArrayList<>();
        to_ret.add(end_node);
        Node<G> actual = previous.get(end_node);
        while(actual != null){
            to_ret.add(actual);
            actual = previous.get(actual);
        }
        Collections.reverse(to_ret);
        return to_ret;
    }


}
