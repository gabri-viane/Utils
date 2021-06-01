/*
 * Copyright 2021 gabri.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ttt.utils.registry.abstracts;

import ttt.utils.registry.Registry;
import ttt.utils.registry.IDRegistry;
import ttt.utils.registry.exception.RegistryException;

/**
 * Rappresenta una classe registrabile tramite un tipo di ID.
 *
 * @author gabri
 * @param <K> Il tipo di ID con cui è registrabile
 */
public abstract class RegistrableEntry<K> {

    private K ID = null;
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
    public final K getID() {
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
     * Registra un oggetto. Solo la classe {@link IDRegistry} tramite il metodo {@link IDRegistry#registerEntry(planetarium.contents.registry.abstracts.RegistrableEntry, java.lang.String)
     * } può registrare l'ID.
     *
     * @param id Il nuovo ID
     * @param registry L'unica instanza di register.
     */
    public final void register(K id, Registry<K, ?> registry) {
        if (registry != null && registry.onCall()) {
            if (id != null) {
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
    public final void remove(Registry<K, ?> register) {
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
    public final void setEntryName(String name, Registry<K, ?> registry) {
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
