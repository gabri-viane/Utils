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
package ttt.utils.registry.events;

import java.util.ArrayList;
import ttt.utils.registry.Registry;
import ttt.utils.registry.abstracts.RegistrableEntry;

/**
 *
 * @author gabri
 */
public abstract class RegistryEvent<K> {

    private final ArrayList<RegistryListener<K>> listeners = new ArrayList<>();

    /**
     * Aggiungi un {@link IDRegistryListener}.
     *
     * @param rl Listener
     */
    public final void addListener(RegistryListener<K> rl) {
        if (rl != null) {
            listeners.add(rl);
        }
    }

    /**
     * Rimuovi un {@link IDRegistryListener}.
     *
     * @param rl Listener
     */
    public final void removeListener(RegistryListener<K> rl) {
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
    public final void elementRegistered(RegistrableEntry<K> re, Registry<K, ?> r) {
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
    public final void elementRemoved(RegistrableEntry<K> re, Registry<K, ?> r) {
        if (r != null && r.onCall()) {
            listeners.forEach(l -> l.onElementRemoved(re));
        }
    }
}
