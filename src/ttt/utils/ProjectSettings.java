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
package ttt.utils;

import java.util.Locale;

/**
 * Impostazioni generali del progetto.
 *
 * @author TTT
 */
public class ProjectSettings {

    /**
     * Imposta lingua del progetto. Per ora è supportato l'Italiano e l'Inglese
     * (britannico e americano).
     */
    public static Locale MAIN_PROGRAMM_LOCALE = Locale.ENGLISH;
    /**
     * Se vero stampa delle scritte pre-impostate nella console quando vengono
     * chiesti dei valori in input.
     */
    public static boolean PROGRAM_DEFAULT_OBJECT_OUTPUT_PHRASE = true;
    /**
     * Se vero stampa il il nome della dell'oggetto richiesto in input.
     */
    public static boolean PROGRAM_SHOW_OBJECT_INPUT = true;
    /**
     * Se vero stampa il il nome della dell'oggetto richiesto in input.
     */
    public static boolean PROGRAM_SHOW_VARIABLE_INPUT_TYPE_OUTPUT = true;
}
