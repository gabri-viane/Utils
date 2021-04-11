/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.registry.abstracts;

import net.vnleng.utils.registry.Registry;
import net.vnleng.utils.registry.exception.RegistryException;

/**
 * Classe che deve essere implementata da tutti gli oggetti che si vogliono fare
 * registrare.
 *
 * @author TTT
 */
public abstract class RegistrableEntry {

    private long ID = -1;
    private boolean registered = false;
    private Object me;
    private String entry_name = null;

    /**
     * L'ID è assegnato tramite {@link }. Di default l'ID che viene ritornato è
     * -1, altrimenti se l'oggetto è stato registrato viene ritornato l'ID
     * relativo.
     *
     * @return L'ID del corpo celeste se è stato registrato, altrimenti -1.
     */
    public final Long getID() {
        return ID;
    }

    /**
     * Un oggetto deve essere registrato prima di poter essere usato tramite il
     * registro. Un oggetto viene registrato quando gli viene assegnato un ID.
     *
     * @return <code>true</code> se è stato registrato, altrimenti
     * <code>false</code>
     */
    public final boolean isRegistered() {
        return registered;
    }

    /**
     * Registra un oggetto. Solo la classe {@link Registry} tramite il
     * metodo {@link Registry#registerEntry(planetarium.contents.registry.abstracts.RegistrableEntry, java.lang.String)
     * } può registrare l'ID.
     *
     * @param id Il nuovo ID
     * @param registry L'unica instanza di register.
     */
    public final void register(long id, Registry registry) {
        if (registry != null && registry.onCall()) {
            if (id >= 0) {
                registered = true;
                ID = id;
                return;
            } else {
                throw new RegistryException("L'ID assegnato non è valido.");
            }
        }
        throw new RegistryException("Il registro non è valido.");
    }

    /**
     * Serve per rimuovere l'oggetto dal registro.
     *
     * @param register Il registro.
     */
    public final void remove(Registry register) {
        if (register != null && register.onCall()) {
            if (registered) {
                registered = false;
                ID = -1;
                return;
            } else {
                throw new RegistryException("L'elemento non è stato registrato.");
            }
        }
        throw new RegistryException("Il registro non è valido.");
    }

    /**
     * Ritorna l'oggetto che viene definito registrabile.
     *
     * @return L'oggetto.
     */
    public final Object getSubclassObject() {
        return me;
    }

    /**
     * Imposta l'oggetto che viene definito registrabile.
     *
     * @param o L'oggetto che viene restituito per eseguire i cast.
     */
    public final void setSubclassObject(Object o) {
        if (me == null) {
            me = o;
        }
    }

    /**
     * Imposta (solo una volta) il nome dell'entrata.
     *
     * @param name Il nome.
     * @param registry L'unica instanza di register.
     */
    public final void setEntryName(String name, Registry registry) {
        if (registry != null && registry.onCall() && entry_name == null) {
            entry_name = name;
        }
    }

    /**
     * Restituisce il nome con cui è stato registrato quest'oggetto
     * all'assegnazione del codice identificativo del registro.
     *
     * @return Il nome dell'entry
     */
    public final String getEntryName() {
        return entry_name;
    }

}
