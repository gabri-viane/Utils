/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.registry.events;

import java.util.ArrayList;
import net.vnleng.utils.registry.Registry;
import net.vnleng.utils.registry.abstracts.RegistrableEntry;

/**
 * Classe che scatena gli eventi riguardanti il registro.
 *
 * @author TTT
 */
public final class RegistryEvent {

    private static final RegistryEvent re = new RegistryEvent();
    private final ArrayList<RegistryListener> listeners;

    private RegistryEvent() {
        listeners = new ArrayList<>();
    }

    /**
     * Restituisce l'unica istanza di questa classe.
     *
     * @return L'istanza di questa classe.
     */
    public static RegistryEvent getIstance() {
        return re;
    }

    /**
     * Aggiungi un {@link RegistryListener}.
     *
     * @param rl Listener
     */
    public void addListener(RegistryListener rl) {
        if (rl != null) {
            listeners.add(rl);
        }
    }

    /**
     * Rimuovi un {@link RegistryListener}.
     *
     * @param rl Listener
     */
    public void removeListener(RegistryListener rl) {
        if (rl != null) {
            listeners.remove(rl);
        }
    }

    /**
     * Avverte tutti i listeners in ascolto che un elemento è stato registrato.
     *
     * @param re L'elemento aggiunto.
     * @param r L'istanza del registro
     */
    public void elementRegistered(RegistrableEntry re, Registry r) {
        if (r != null && r.onCall()) {
            listeners.forEach(l -> l.onElementRegistered(re));
        }
    }

    /**
     * Avverte tutti i listeners in ascolto che un elemento è stato rimosso dal
     * registro.
     *
     * @param re L'elemento rimosso.
     * @param r L'istanza del registro
     */
    public void elementRemoved(RegistrableEntry re, Registry r) {
        if (r != null && r.onCall()) {
            listeners.forEach(l -> l.onElementRemoved(re));
        }
    }
}
