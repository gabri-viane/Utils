/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.annotations;

import ttt.utils.xml.engine.XMLEngine;
import ttt.utils.xml.engine.interfaces.IXMLTag;

/**
 *
 * @author TTT
 */
public @interface Element {

    /**
     * Il nome dell'Elemento.
     *
     * @return
     */
    public String Name();

    /**
     * Se può avere delle tags nella sua definizione. ({@link IXMLTag}) Il
     * valore d default è {@code true}.
     *
     * @return {@code true} se la classe {@link XMLEngine} deve tenere conto di
     * eventuali attributi.
     */
    public boolean CanHaveTags() default true;

    /**
     * Se può avere un valore associato alla sua definizione. Il valore d
     * default è {@code true}.
     *
     * @return {@code true} se la classe {@link XMLEngine} deve tenere conto di
     * un eventuale valore associato.
     */
    public boolean CanHaveValue() default true;
}
