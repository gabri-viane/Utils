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
package ttt.utils.console.input;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import ttt.utils.ProjectSettings;
import ttt.utils.console.input.interfaces.Validator;
import static ttt.utils.console.output.GeneralFormatter.printOut;

/**
 * Gestisce tutti gli input base. Tramite alcune funzioni si può definire se
 * l'inserimento di un dato è annullabile.
 *
 * @author TTT
 */
public class ConsoleInput {

    private static ConsoleInput istance;
    private static final ResourceBundle input_data = ResourceBundle.getBundle("ttt/utils/resources/i18n/input/input_data");
    private final Scanner input;

    private ConsoleInput() {
        input = new Scanner(System.in);
        input.useLocale(ProjectSettings.MAIN_PROGRAMM_LOCALE); // Si usa il punto e non la virgola.
    }

    public static ConsoleInput getInstance() {
        if (istance == null) {
            istance = new ConsoleInput();
        }
        return istance;
    }

    /**
     * Legge un double in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public double readDouble(String question) {
        return readDouble(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore double. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Double> readDouble(String question, boolean skippable, String skippable_keyword) {
        return readDouble(question, skippable, skippable_keyword, null);
    }

    /**
     * Chiede di inserire un valore double.Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @param v Il validatore del valore.
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Double> readDouble(String question, boolean skippable, String skippable_keyword, Validator<Double> v) {
        Double d = null;
        printOut(question + input_data.getString("space"), false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    d = Double.parseDouble(in.replaceAll(",", "\\."));
                    if (v != null) {
                        v.validate(d);
                    }
                } else {
                    d = input.nextDouble();
                }
                break;
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input")
                        + (skippable ? input_data.getString("annulable_1") + skippable_keyword + input_data.getString("annulable_2") : "")
                        + input_data.getString("ask_input"), false, true);
            }
        }
        input.nextLine();
        return Optional.ofNullable(d);
    }

    /**
     * Legge un float in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public float readFloat(String question) {
        return readFloat(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore float. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Float> readFloat(String question, boolean skippable, String skippable_keyword) {
        return readFloat(question, skippable, skippable_keyword, null);
    }

    /**
     * Chiede di inserire un valore float.Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @param v Il validatore del valore.
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Float> readFloat(String question, boolean skippable, String skippable_keyword, Validator<Float> v) {
        Float f = null;
        printOut(question + input_data.getString("space"), false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    f = Float.parseFloat(in.replaceAll(",", "\\."));
                    if (v != null) {
                        v.validate(f);
                    }
                } else {
                    f = input.nextFloat();
                }
                break;
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input")
                        + (skippable ? input_data.getString("annulable_1") + skippable_keyword + input_data.getString("annulable_2") : "")
                        + input_data.getString("ask_input"), false, true);
            }
        }
        input.nextLine();
        return Optional.ofNullable(f);
    }

    /**
     * Legge un intero in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public int readInteger(String question) {
        return readInteger(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore intero. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Integer> readInteger(String question, boolean skippable, String skippable_keyword) {
        return readInteger(question, skippable, skippable_keyword, null);
    }

    /**
     * Chiede di inserire un valore intero.Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @param v Il validatore del valore.
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Integer> readInteger(String question, boolean skippable, String skippable_keyword, Validator<Integer> v) {
        Integer i = null;
        printOut(question + input_data.getString("space"), false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    i = Integer.parseInt(in);
                    if (v != null) {
                        v.validate(i);
                    }
                } else {
                    i = input.nextInt();
                }
                break;
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input")
                        + (skippable ? input_data.getString("annulable_1") + skippable_keyword + input_data.getString("annulable_2") : "")
                        + input_data.getString("ask_input"), false, true);
            }
        }

        input.nextLine();

        return Optional.ofNullable(i);
    }

    /**
     * Legge un long in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public long readLong(String question) {
        return readLong(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore long. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Long> readLong(String question, boolean skippable, String skippable_keyword) {
        return readLong(question, skippable, skippable_keyword, null);
    }

    /**
     * Chiede di inserire un valore long.Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @param v Il validatore del valore.
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Long> readLong(String question, boolean skippable, String skippable_keyword, Validator<Long> v) {
        Long l = null;
        printOut(question + input_data.getString("space"), false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    l = Long.parseLong(in);
                    if (v != null) {
                        v.validate(l);
                    }
                } else {
                    l = input.nextLong();
                }
                break;
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input")
                        + (skippable ? input_data.getString("annulable_1") + skippable_keyword + input_data.getString("annulable_2") : "")
                        + input_data.getString("ask_input"), false, true);
            }
        }
        input.nextLine();
        return Optional.ofNullable(l);
    }

    /**
     * Legge una stringa in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public String readString(String question) {
        return readString(question, false, null).get();
    }

    /**
     * Chiede di inserire una stringa. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<String> readString(String question, boolean skippable, String skippable_keyword) {
        return readString(question, skippable, skippable_keyword, null);
    }

    /**
     * Chiede di inserire una stringa.Si può uscire dal ciclo tramite una parola
     * specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @param v Il validatore del valore.
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<String> readString(String question, boolean skippable, String skippable_keyword, Validator<String> v) {
        String s = null;
        printOut(question + input_data.getString("space"), false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.nextLine();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    s = in;
                    if (v != null) {
                        v.validate(s);
                    }
                } else {
                    s = input.nextLine();
                }
                break;
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input")
                        + (skippable ? input_data.getString("annulable_1") + skippable_keyword + input_data.getString("annulable_2") : "")
                        + input_data.getString("ask_input"), false, true);
            }
        }
        return Optional.ofNullable(s);
    }

    /**
     * Legge una stringa in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public char readCharacter(String question) {
        return readCharacter(question, false, null).get();
    }

    /**
     * Chiede di inserire una stringa. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Character> readCharacter(String question, boolean skippable, String skippable_keyword) {
        return readCharacter(question, skippable, skippable_keyword, null);
    }

    /**
     * Chiede di inserire una stringa.Si può uscire dal ciclo tramite una parola
     * specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal ciclo (se e solo se {@code skippable = true}).
     * @param v
     * @return Può restituire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Character> readCharacter(String question, boolean skippable, String skippable_keyword, Validator<Character> v) {
        Character c = null;
        printOut(question + input_data.getString("space"), false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    c = in.charAt(0);
                    if (v != null) {
                        v.validate(c);
                    }
                } else {
                    c = input.nextLine().charAt(0);
                }
                break;
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input")
                        + (skippable ? input_data.getString("annulable_1") + skippable_keyword + input_data.getString("annulable_2") : "")
                        + input_data.getString("ask_input"), false, true);
            }
        }
        //input.nextLine();
        return Optional.ofNullable(c);
    }

    /**
     * Legge un boolean. Se la stringa inizia con "y" o "s" allora ritorna
     * {@code true}, se inizia con "n" ritorna {@code false}.
     *
     * @param question La domanda.
     * @return Vero o Falso.
     */
    public boolean readBoolean(String question) {
        Boolean b = null;
        printOut(question + input_data.getString("yes_no"), false, false);
        while (true) {
            try {
                String in = input.nextLine().toLowerCase();
                if (in.startsWith(input_data.getString("yes_answer"))) {
                    b = true;
                    break;
                } else if (in.startsWith(input_data.getString("no_answer"))) {
                    b = false;
                    break;
                } else {
                    printOut(input_data.getString("invalid_input") + input_data.getString("yes_no") + input_data.getString("ask_input"), false, true);
                }
            } catch (NoSuchElementException | NumberFormatException ex) {
                input.nextLine();
                printOut(input_data.getString("invalid_input") + input_data.getString("yes_no") + input_data.getString("ask_input"), false, true);
            }
        }
        return b;
    }

}
