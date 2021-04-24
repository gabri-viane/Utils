/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.registry.exception;

/**
 * Eccezione causate dal registro degli elementi.
 *
 * @author TTT
 */
public class RegistryException extends RuntimeException {

    public RegistryException(String mesage) {
        super(mesage);
    }
}
