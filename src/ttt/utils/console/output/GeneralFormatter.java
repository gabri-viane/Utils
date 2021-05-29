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
package ttt.utils.console.output;

import java.io.PrintStream;

/**
 * Gestisce la stampa in console per tutta la libreria.
 *
 * @author TTT
 */
public class GeneralFormatter {

    private static int INDENTS = 0;

    private GeneralFormatter(){
      
    }

    /**
     * Incrementa le indentazioni (globalmente).
     */
    public static void incrementIndents() {
        INDENTS++;
    }

    /**
     * Decrementa le indentazioni (globalmente).
     */
    public static void decrementIndents() {
        if (INDENTS > 0) {
            INDENTS--;
        }
    }

    /**
     * Ritorna le indentazioni.
     *
     * @return Le indentazioni.
     */
    public static String getIndentes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < INDENTS; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    /**
     * Stampa in output un testo.
     *
     * @param message Il messaggio da stampare.
     * @param newline {@code true} se si vuole tornare a capo.
     * @param error {@code true} se si vuole stampare nel {@link PrintStream}
     * degli errori, altrimenti in quello normale di output.
     */
    public static void printOut(String message, boolean newline, boolean error) {
        String msg = getIndentes() + message + (newline ? "\n" : "");
        if (error) {
            System.err.print(msg);
        } else {
            System.out.print(msg);
        }
    }
}
