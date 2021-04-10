/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.menu.utils;

/**
 *
 * @author gabri
 */
public class Pair<K, V> {

    private final K key;
    private final V value;

    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}
