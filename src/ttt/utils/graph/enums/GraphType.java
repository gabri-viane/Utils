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
package ttt.utils.graph.enums;

/**
 * Possibili rappresentazioni di grafi.
 *
 * @author TTT
 */
public enum GraphType {

    /**
     * Rappresenta un grafo generico.
     */
    GENERIC,
    /**
     * Rappresenta un grafo generico di soli archi bidirezionali.
     */
    BIDIRECTIONAL_GENERIC,
    /**
     * Rappresenta un grafo generico di soli archi monodirezionali.
     */
    MONODIRECTIONAL_GENERIC,
    /**
     * Rappresenta un grafo ad albero.
     */
    TREE,
    /**
     * Rappresenta un grafo ad albero radicato.
     */
    ROOTED_TREEE,
    /**
     * Rappresenta un grafo ad albero ordinato.
     */
    ORDERED_TREE,
    /**
     * Rappresenta un grafo ad albero binario.
     */
    BINARY_TREE,
    /**
     * Rappresenta un grafo estratto da un altro grafo ad albero.
     */
    SUB_TREE
}
