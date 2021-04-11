/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.registry.events;

import net.vnleng.utils.registry.abstracts.RegistrableEntry;

/**
 * Semplice Listener del registro, utile per essere segnalati di eliminazioni o
 * aggiunte di elementi nel registro.
 *
 * @author TTT
 */
public interface RegistryListener {

    /**
     * Viene chiamato ogni volta che un elemento viene registrato.
     *
     * @param re L'elemento registrato.
     */
    public void onElementRegistered(RegistrableEntry re);

    /**
     * Viene chiamato ogni volta che un elemento viene rimosso dal registro.
     *
     * @param re L'elemento rimosso.
     */
    public void onElementRemoved(RegistrableEntry re);
}
