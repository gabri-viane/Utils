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
package ttt.utils.graph.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import ttt.utils.console.menu.utils.Pair;
import ttt.utils.graph.elements.GraphPath;
import ttt.utils.graph.elements.Link;
import ttt.utils.graph.elements.Node;
import ttt.utils.graph.enums.GraphType;

/**
 *
 * @author TTT
 * @param <T> Il tipo di nodi presenti.
 * @param <K> Il tipo di archi presenti.
 */
public class Graph<T, K> {

    protected Set<Link<T, K>> links;
    protected Set<Node<T, K>> nodes;
    protected GraphType type = GraphType.GENERIC;

    public Graph() {
        links = new HashSet<>();
        nodes = new HashSet<>();
    }

    public void addLink(Link<T, K> l) {
        boolean rs = links.add(l);
        if (rs) {
            Pair<Node<T, K>, Node<T, K>> nodes1 = l.getNodes();
            nodes.add(nodes1.getKey());
            nodes.add(nodes1.getValue());
        }
    }

    public void removeLink(Link<T, K> l) {
        boolean remove = links.remove(l);
        if (remove) {
            Pair<Node<T, K>, Node<T, K>> ns = l.getNodes();
            if (!hasConnections(ns.getKey())) {
                nodes.remove(ns.getKey());
            } else {
                ns.getKey().removeLink(l);
            }
            if (!hasConnections(ns.getValue())) {
                nodes.remove(ns.getValue());
            } else {
                ns.getKey().removeLink(l);
            }
        }
    }

    public boolean hasConnections(Node<T, K> n) {
        return findNode(n).hasPath();
    }

    public GraphPath<T, K> findNode(Node<T, K> n) {
        ArrayList<Node<T, K>> done_nodes = new ArrayList<>();
        GraphPath<T, K> gp = new GraphPath<>();
        gp = nextLink(links, done_nodes, gp, n);
        return gp != null ? gp : new GraphPath<>();
    }

    protected GraphPath<T, K> nextLink(Collection<Link<T, K>> cl, ArrayList<Node<T, K>> sources, GraphPath<T, K> result, Node<T, K> to_find) {
        Iterator<Link<T, K>> it = cl.iterator();
        while (it.hasNext()) {
            Link<T, K> next = it.next();
            Pair<Node<T, K>, Node<T, K>> ns = next.getNodes();
            if (ns.getValue() == to_find) {
                result.append(ns.getKey());
                result.append(to_find);
                return result;
            }
            if (!sources.contains(ns.getValue())) {
                sources.add(ns.getKey());
                sources.add(ns.getValue());
                result.append(ns.getKey());
                GraphPath<T, K> possible_path = nextLink(ns.getValue().getLinks(), sources, result.split(), to_find);
                if (possible_path != null) {
                    result = possible_path;
                    return result;
                }
            }else if(!sources.contains(ns.getKey())){
                sources.add(ns.getValue());
                sources.add(ns.getKey());
                result.append(ns.getValue());
                GraphPath<T, K> possible_path = nextLink(ns.getKey().getLinks(), sources, result.split(), to_find);
                if (possible_path != null) {
                    result = possible_path;
                    return result;
                }
            }
        }
        return null;
    }
    
}
