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
package ttt.utils.console.menu.utils;

/**
 * Rappresenta una semplice coppia di Chiave-Valore.
 *
 * @author TTT
 * @param <K> La chiave
 * @param <V> Il valore
 */
public class Pair<K, V> {

    private final K key;
    private final V value;

    /**
     * Inizializza un coppia.
     *
     * @param k La chiave
     * @param v Il valore.
     */
    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    /**
     * Ritorna la chiave.
     *
     * @return La chiave impostata.
     */
    public K getKey() {
        return key;
    }

    /**
     * Ritorna il valore.
     *
     * @return Il valore impostato.
     */
    public V getValue() {
        return value;
    }

}
