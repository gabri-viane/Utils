/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.enums;

/**
 * I tipi di metodi che possono essere utilizzati da un Engine per completare e
 * richiedere valori.
 *
 * @author TTT
 */
public enum MethodType {
    /**
     * Segnala un metodo di tipo GET, con valore di ritorno.
     */
    GET,
    /**
     * Segnala un metodo di tipo SET, senza necessariamente un metodo di ritorno
     * ma che deve rispettare il numero di parametri e i tipi definiti
     * dall'engine che deve utilizzare il metodo.
     */
    SET,
    /**
     * Segnala un metodo che viene chiamato solamente dopo aver chiamato tutti i
     * metodi set e aver inizializzato i valori base.<br>
     * Non necessita un ritorno ma deve rispettare il numero di parametri e i
     * tipi definiti dall'engine che deve utilizzare il metodo.
     */
    CALC,
    EVENT,
    VARIABLE_ONLY
}
