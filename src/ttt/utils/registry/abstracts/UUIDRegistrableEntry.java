/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.registry.abstracts;

import java.util.UUID;
import ttt.utils.registry.Registry;
import ttt.utils.registry.UUIDRegistry;
import ttt.utils.registry.exception.RegistryException;

/**
 *
 * @author gabri
 */
public abstract class UUIDRegistrableEntry {

    private UUID ID = null;
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
    public final UUID getUUID() {
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
     * Registra un oggetto. Solo la classe {@link Registry} tramite il metodo {@link Registry#registerEntry(planetarium.contents.registry.abstracts.RegistrableEntry, java.lang.String)
     * } può registrare l'ID.
     *
     * @param id Il nuovo ID
     * @param registry L'unica instanza di register.
     */
    public final void register(UUID id, UUIDRegistry registry) {
        if (registry != null && registry.onCall()) {
            if (id != null) {
                registered = true;
                ID = id;
                return;
            } else {
                throw new RegistryException("L'UUID assegnato non è valido.");
            }
        }
        throw new RegistryException("Il registro non è valido.");
    }

    /**
     * Serve per rimuovere l'oggetto dal registro.
     *
     * @param register Il registro.
     */
    public final void remove(UUIDRegistry register) {
        if (register != null && register.onCall()) {
            if (registered) {
                registered = false;
                ID = null;
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
    public final void setEntryName(String name, UUIDRegistry registry) {
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
