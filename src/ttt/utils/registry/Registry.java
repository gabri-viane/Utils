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
package ttt.utils.registry;

import java.util.HashMap;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.registry.abstracts.RegistrableEntry;
import ttt.utils.registry.events.RegistryEvent;
import ttt.utils.registry.interfaces.Index;

/**
 * Registro generico che permette di memorizzare degli oggetti con una chiave e
 * la chiave con una stringa per facilitare la ricerca.
 *
 * @author gabri
 * @param <K>
 * @param <V>
 */
public abstract class Registry<K, V extends RegistrableEntry<K>> implements Index<K> {

    final RegistryEvent<K> event;
    final Registry<K, V> register_key = this;
    K currentID = null;

    final HashMap<K, V> registry = new HashMap<>();
    final HashMap<String, K> secondary_registry = new HashMap<>();

    public Registry(RegistryEvent<K> event) {
        this.event = event;
    }

    /**
     * Serve solo come test.
     *
     * @return <code>true</code>, sempre
     */
    public abstract boolean onCall();

    /**
     * Registra un nuovo elemento e gli assegna l'ID.L'oggetto deve estendere la
     * classe astratta {@link RegistrableEntry}.
     *
     * @param re L'oggetto da registrare.
     * @param entry_name Il nome con cui l'oggetto viene registrato (unico,
     * altrimenti la registrazione fallisce).
     * @return <code>true</code> se la registrazione avviene con successo,
     * altrimenti <code>false</code>.
     */
    public abstract boolean registerEntry(V re, String entry_name);

    /**
     * Rimuove un elemento dal registro. Gli elementi rimossi non vengono più
     * considerati.
     *
     * @param re L'elemento che deve esser rimosso dal registro ed escluso dai
     * calcoli.
     * @return <code>true</code> se viene rimosso con successo, altrimenti
     * <code>false</code>.
     */
    public abstract boolean removeEntry(V re);

    /**
     * Ritorna un {@link RegistrableEntry} dato l'ID corrispondente.
     *
     * @param ID L'ID da cercare.
     * @return {@code null} se non esiste nessun elemento con quell'ID
     * altrimenti un riferimento ad un {@link RegistrableEntry}.
     */
    public final V getEntry(K ID) {
        return registry.get(ID);
    }

    /**
     * Ritorna un {@link RegistrableEntry} dato il nome di registrazione
     * corrispondente.
     *
     * @param name Il nome da cercare.
     * @return {@code null} se non esiste nessun elemento con quel nome
     * altrimenti un riferimento ad un {@link RegistrableEntry}.
     */
    public final V getEntry(String name) {
        K val_id = secondary_registry.get(name);
        return val_id != null ? registry.get(val_id) : null;
    }

    /**
     * Stampa una rappresentazione a lista del registro:
     * <p>
     * ID | {@link RegistrableEntry#toString() }
     */
    public void printRegistry() {
        registry.forEach((t, u) -> {
            GeneralFormatter.printOut("ID: " + t + "\t\t" + u.toString(), true, false);
        });
    }

    /**
     * Ritorna il gestore di eventi di questo registro.
     *
     * @return L'istanza dell'EventHandler associata a questo registro.
     */
    public final RegistryEvent<K> getEventHandler() {
        return event;
    }

    @Override
    public final K getLastID() {
        return currentID;
    }
}
