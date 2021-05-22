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
package ttt.utils.xml.document.structure.rules;

/**
 *
 * @author TTT
 */
public enum Rules {
    /**
     * L'elemento deve essere presente per un massimo di un'occorrenza.
     */
    ONE(1, 1),
    /**
     * L'elemento può essere presente. Se presente non ci possono essere più di
     * un'occorrenza.
     */
    ZERO_TO_ONE(0, 1),
    /**
     * L'elemento può essere presente. Ci possono essere da 0 a infinite
     * occorrenze.
     */
    ZERO_TO_INFINITE(0, -1),
    /**
     * L'elemento deve essere presente. Ci deve essere almeno un'elemento.
     */
    ONE_TO_INFINITE(1, -1);

    int min;
    int max;

    private Rules(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Controlla che la quantità sia compresa nella regola.
     *
     * @param amount La quantità da controllare.
     * @return {@code true} nel caso il valore è corrette.
     */
    public boolean valid(long amount) {
        if (max < 0) {
            return amount >= min;
        }
        return (amount >= min && amount <= max);
    }

    /**
     * Controlla che la quantità sia compresa nella regola.
     *
     * @param amount La quantità da controllare.
     * @return {@code true} nel caso il valore è corrette.
     */
    public boolean valid(int amount) {
        if (max < 0) {
            return amount >= min;
        }
        return (amount >= min && amount <= max);
    }

    /**
     * Ritorna la regola più appropriata per un elemento.
     *
     * @param count La quantità possibile di elementi.
     * @param zero_allowed Se è possibile che l'elemento non sia presente.
     * @return La regola più opportuna.
     */
    public static Rules getAppropriate(long count, boolean zero_allowed) {
        if (count <= 1) {
            return zero_allowed ? ZERO_TO_ONE : ONE;
        } else {
            return zero_allowed ? ZERO_TO_INFINITE : ONE_TO_INFINITE;
        }
    }
}
