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
package ttt.utils.graph.elements;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

/**
 *
 * @author TTT
 * @param <T>
 * @param <K>
 */
public class GraphPath<T, K> {

    private final LinkedHashSet<Node<T, K>> path;

    public GraphPath() {
        path = new LinkedHashSet<>();
    }

    private GraphPath(LinkedHashSet<Node<T, K>> path) {
        this.path = new LinkedHashSet<>();
        path.stream().forEachOrdered(n -> this.path.add(n));
    }

    public boolean append(Node<T, K> n) {
        return path.add(n);
    }

    public Stream<Node<T, K>> stream() {
        return path.stream();
    }

    public boolean hasPath() {
        return path.size() > 0;
    }

    public boolean reached(Node<T, K> n) {
        return path.contains(n);
    }

    public GraphPath<T, K> split() {
        return new GraphPath<>(path);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        path.stream().forEachOrdered(n -> sb.append(n.getName()).append(" > "));
        if (sb.length() > 0) {
            sb.delete(sb.length() - 3, sb.length());
        } else {
            sb.append("No valid path generated");
        }
        return sb.toString();
    }

}
