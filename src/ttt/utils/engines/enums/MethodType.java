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
package ttt.utils.engines.enums;

/**
 * I tipi di metodi che possono essere utilizzati da un Engine per completare e
 * richiedere valori.
 *
 * @author TTT
 */
public enum MethodType {
    /**
     * Segnala un metodo di tipo GET, con valore di ritorno.
     */
    GET,
    /**
     * Segnala un metodo di tipo SET, senza necessariamente un metodo di ritorno
     * ma che deve rispettare il numero di parametri e i tipi definiti
     * dall'engine che deve utilizzare il metodo.
     */
    SET,
    /**
     * Segnala un metodo che viene chiamato solamente dopo aver chiamato tutti i
     * metodi set e aver inizializzato i valori base.<br>
     * Non necessita un ritorno ma deve rispettare il numero di parametri e i
     * tipi definiti dall'engine che deve utilizzare il metodo.
     */
    CALC,
    EVENT
}
