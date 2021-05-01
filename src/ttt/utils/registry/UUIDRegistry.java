/*
 * Copyright 2021 TTT.
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
import java.util.UUID;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.registry.abstracts.RegistrableEntry;
import ttt.utils.registry.abstracts.UUIDRegistrableEntry;
import ttt.utils.registry.events.UUIDRegistryEvent;
import ttt.utils.registry.exception.RegistryException;
import ttt.utils.registry.interfaces.IndexUUID;

/**
 * Versione del registro {@link Registry} che utilizza la classe {@link UUID}
 * come ID invece che un valore {@link Long}.
 *
 * @author TTT
 */
public class UUIDRegistry implements IndexUUID {

    private static final UUIDRegistry register_key = new UUIDRegistry();
    private static UUID currentID = UUID.randomUUID();

    private static final HashMap<String, UUIDRegistrableEntry> registry = new HashMap<>();
    private static final HashMap<String, String> secondary_registry = new HashMap<>();

    private UUIDRegistry() {

    }

    /**
     * Serve solo come test.
     *
     * @return <code>true</code>, sempre
     */
    public boolean onCall() {
        return true;
    }

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
    public static boolean registerEntry(UUIDRegistrableEntry re, String entry_name) {
        try {
            if (re != null && !re.isRegistered()
                    && entry_name != null && !"".equals(entry_name.trim()) && !secondary_registry.containsKey(entry_name)) {
                re.register(currentID, register_key);
                re.setEntryName(entry_name, register_key);
                secondary_registry.put(entry_name, currentID.toString());
                registry.put(currentID.toString(), re);
                currentID = UUID.randomUUID();
                UUIDRegistryEvent.getInstance().elementRegistered(re, register_key);
                return true;
            }
            return false;
        } catch (RegistryException ex) {
            return false;
        }
    }

    /**
     * Rimuove un elemento dal registro. Gli elementi rimossi non vengono più
     * considerati.
     *
     * @param re L'elemento che deve esser rimosso dal registro ed escluso dai
     * calcoli.
     * @return <code>true</code> se viene rimosso con successo, altrimenti
     * <code>false</code>.
     */
    public static boolean removeEntry(UUIDRegistrableEntry re) {
        if (re != null && registry.containsValue(re)) {
            registry.remove(re.getUUID().toString());
            secondary_registry.remove(re.getEntryName());
            re.remove(register_key);
            UUIDRegistryEvent.getInstance().elementRemoved(re, register_key);
            return true;
        }
        return false;
    }

    /**
     * Ritorna un {@link RegistrableEntry} dato l'ID corrispondente.
     *
     * @param UUID L'UUID da cercare.
     * @return {@code null} se non esiste nessun elemento con quell'ID
     * altrimenti un riferimento ad un {@link RegistrableEntry}.
     */
    public static UUIDRegistrableEntry getEntry(String UUID) {
        return registry.get(UUID);
    }

    /**
     * Ritorna un {@link RegistrableEntry} dato il nome di registrazione
     * corrispondente.
     *
     * @param name Il nome da cercare.
     * @return {@code null} se non esiste nessun elemento con quel nome
     * altrimenti un riferimento ad un {@link RegistrableEntry}.
     */
    public static UUIDRegistrableEntry getEntryByName(String name) {
        String val_id = secondary_registry.get(name);
        return val_id != null ? registry.get(val_id) : null;
    }

    /**
     * Stampa una rappresentazione a lista del registro:
     * <p>
     * ID | {@link RegistrableEntry#toString() }
     */
    public static void printRegistry() {
        registry.forEach((t, u) -> {
            GeneralFormatter.printOut("ID: " + t + "\t\t" + u.toString(), true, false);
        });
    }

    @Override
    public UUID getLastUUID() {
        return currentID;
    }

}
