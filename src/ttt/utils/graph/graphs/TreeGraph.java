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

import ttt.utils.graph.elements.Node;
import ttt.utils.graph.enums.GraphType;
import ttt.utils.graph.exceptions.InvalidGraphFormat;

/**
 *
 * @author TTT
 * @param <T>
 * @param <K>
 */
public class TreeGraph<T, K> extends Graph<T, K> {

    protected Node<T, K> root;

    public TreeGraph(Node<T, K> root) {
        super();
        this.root = root;
        type = GraphType.TREE;
    }

    public void validate() throws InvalidGraphFormat {
        /*
        Sono invalidi cicli e link che non sono collegati al resto della struttura. 
        La struttura è considerata valida se collegata al primo link aggiunto.
         */
        if (links.size() > 0) {
            for (Node n : nodes) {
                if (n != root && !hasConnections(n)) {
                    throw new InvalidGraphFormat("L'albero contiene un nodo non raggiungibile: " + n);
                }
            }
            return;
        }
        throw new InvalidGraphFormat("L'albero è vuoto.");
    }

}
