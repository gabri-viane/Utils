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

import ttt.utils.registry.abstracts.IDRegistrableEntry;
import ttt.utils.registry.events.IDRegistryEvent;
import ttt.utils.registry.exception.RegistryException;

/**
 * Versione del registro {@link Registry} che utilizza la classe {@link Long}
 * come ID.
 *
 * @author TTT
 * @param <V>
 */
public final class IDRegistry<V extends IDRegistrableEntry> extends Registry<Long, V> {

    private IDRegistry() {
        super(new IDRegistryEvent());
        currentID = 0L;
    }

    public static <G extends IDRegistrableEntry> IDRegistry<G> buildRegistry() {
        return new IDRegistry<>();
    }

    /**
     * Serve solo come test.
     *
     * @return <code>true</code>, sempre
     */
    @Override
    public boolean onCall() {
        return true;
    }

    /**
     * Registra un nuovo elemento e gli assegna l'ID.L'oggetto deve estendere la
     * classe astratta {@link IDRegistrableEntry}.
     *
     * @param re L'oggetto da registrare.
     * @param entry_name Il nome con cui l'oggetto viene registrato (unico,
     * altrimenti la registrazione fallisce).
     * @return <code>true</code> se la registrazione avviene con successo,
     * altrimenti <code>false</code>.
     */
    @Override
    public boolean registerEntry(V re, String entry_name) {
        try {
            if (re != null && !re.isRegistered()
                    && entry_name != null && !"".equals(entry_name.trim()) && !secondary_registry.containsKey(entry_name)) {
                re.register(currentID, register_key);
                re.setEntryName(entry_name, this);
                secondary_registry.put(entry_name, currentID);
                registry.put(currentID++, re);
                event.elementRegistered(re, this);
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
    @Override
    public boolean removeEntry(V re) {
        if (re != null && registry.containsValue(re)) {
            registry.remove(re.getID());
            secondary_registry.remove(re.getEntryName());
            re.remove(this);
            event.elementRemoved(re, this);
            return true;
        }
        return false;
    }

}
