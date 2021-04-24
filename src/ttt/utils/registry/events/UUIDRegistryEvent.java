/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.registry.events;

import java.util.ArrayList;
import ttt.utils.registry.Registry;
import ttt.utils.registry.abstracts.UUIDRegistrableEntry;

/**
 * Classe che scatena gli eventi riguardanti il registro.
 *
 * @author TTT
 */
public final class UUIDRegistryEvent {

    private static final UUIDRegistryEvent re = new UUIDRegistryEvent();
    private final ArrayList<UUIDRegistryListener> listeners;

    private UUIDRegistryEvent() {
        listeners = new ArrayList<>();
    }

    /**
     * Restituisce l'unica istanza di questa classe.
     *
     * @return L'istanza di questa classe.
     */
    public static UUIDRegistryEvent getIstance() {
        return re;
    }

    /**
     * Aggiungi un {@link RegistryListener}.
     *
     * @param rl Listener
     */
    public void addListener(UUIDRegistryListener rl) {
        if (rl != null) {
            listeners.add(rl);
        }
    }

    /**
     * Rimuovi un {@link RegistryListener}.
     *
     * @param rl Listener
     */
    public void removeListener(UUIDRegistryListener rl) {
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
    public void elementRegistered(UUIDRegistrableEntry re, Registry r) {
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
    public void elementRemoved(UUIDRegistrableEntry re, Registry r) {
        if (r != null && r.onCall()) {
            listeners.forEach(l -> l.onElementRemoved(re));
        }
    }
}
