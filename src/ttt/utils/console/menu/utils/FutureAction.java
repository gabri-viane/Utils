/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.console.menu.utils;

/**
 *
 * @author TTT
 * @param <P>
 */
@FunctionalInterface
public interface FutureAction<P> {

    public P onSelected();
}