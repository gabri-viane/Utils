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
package ttt.utils.console.menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import ttt.utils.console.input.ConsoleInput;
import ttt.utils.console.menu.utils.Pair;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.console.menu.utils.FutureAction;

/**
 *
 * @author TTT
 * @param <P> Tipo di menu, nel caso ritorna un valore
 */
public abstract class Menu<P> {

    private static final ResourceBundle menu_bundle = ResourceBundle.getBundle("ttt/utils/resources/i18n/menu/menu_bundle");

    private final ArrayList<Pair<String, FutureAction<P>>> menu = new ArrayList<>();
    private final ArrayList<FutureAction<P>> to_execute_later = new ArrayList<>();

    private final String title;
    private boolean quitMenu = false;

    /**
     * Inizializza un menù.
     *
     * @param title Il titolo da stampare.
     */
    public Menu(String title) {
        this(title, false);
    }

    /**
     * Inizializza un menù.
     *
     * @param title Il titolo da stampare.
     * @param print_menu_title Se {@code true} allora viene stampata la stringa
     * default del titolo.
     */
    public Menu(String title, boolean print_menu_title) {
        this.title = title;
        init(print_menu_title);
    }

    /**
     * Reimposta il menù.<br>
     * Inserisce nella posizione 1 l'opzione "Esci" che permette di uscire
     * dall'esecuzione del programma chiamando {@link System#exit(int) }.
     */
    public void reset() {
        quitMenu = false;
        menu.clear();
        to_execute_later.clear();
        addOption(menu_bundle.getString("exit"), () -> {
            System.exit(0);
            return null;
        });
    }

    private void init(boolean print_title) {
        if (print_title) {
            GeneralFormatter.printOut(menu_bundle.getString("menu_title"), true, false);
        }
        reset();
    }

    /**
     * Aggiunge una nuova opzione al menu.
     *
     * @param option_message La voce del menu
     * @param action L'azione che verrà eseguita
     */
    public void addOption(String option_message, FutureAction action) {
        if (option_message != null && action != null) {
            menu.add(new Pair<>(option_message, action));
        }
    }

    /**
     * Ogni azione aggiunta tramite questo metodo viene eseguita in modo
     * ritardato dopo che è finita l'esecuzione di un'azione del menu. Viene
     * eseguita perciò dopo che l'utente ha selezionato e completato un'azione e
     * prima che il menù venga ristampato.
     *
     * @param action Azione da eseguire dopo aver eseguito l'azione scelta
     * dall'utente.
     */
    public void addLazyExecutable(FutureAction action) {
        if (action != null) {
            to_execute_later.add(action);
        }
    }

    /**
     * Rimuove l'opzione dal menù all'indice specificato. L'indice parte da 1 e
     * non da 0.
     *
     * @param index Indice da rimuovere
     */
    public void removeOption(int index) {
        if (index > 0 && index <= menu.size()) {
            menu.remove(index - 1);
        }
    }

    /**
     * Trova l'indice di un menu in base al testo contenuto.
     *
     * @param text Testo da cercare tra le voci del menu.
     * @return L'indice dell'opzione.
     */
    public int optionLookup(String text) {
        return menu.indexOf(menu.stream().filter((t) -> {
            return t.getKey().equals(text);
        }).findFirst());
    }

    /**
     * Stampa il menù e attende una risposta.
     */
    public void paintMenu() {
        GeneralFormatter.printOut(title, true, false);
        for (int i = 0; i < menu.size(); i++) {
            GeneralFormatter.printOut("[" + (i + 1) + "] " + menu.get(i).getKey(), true, false);
        }
        System.out.println();
        waitAnswer();
    }

    private boolean autoPrint = true;

    /**
     * Imposta se il menu deve stampare gli spazi tra un aggiornamento e il
     * successivo.
     *
     * @param value {@code false} nel caso si voglia disabilitare. Di default è
     * {@code true}.
     */
    public void autoPrintSpaces(boolean value) {
        autoPrint = value;
    }

    /**
     * Stampa N nuove linee per separare i testi.
     *
     * @param n Il numero di linee da stampare
     */
    private void consoleSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
        }
    }

    /**
     * Attende una risposta valida. Per valida si intende una risposta che
     * faccia parte dell'array {@link Menu#menu} per poi farla eseguire.
     */
    private void waitAnswer() {
        GeneralFormatter.incrementIndents();
        Optional<Integer> op;
        do {
            op = ConsoleInput.getInstance().readInteger(menu_bundle.getString("oper"), false, null);
        } while (op.get() < 1 || op.get() > menu.size());
        GeneralFormatter.decrementIndents();
        if (autoPrint) {
            consoleSpaces(3);
        }
        executeAt(op.get() - 1);
        if (autoPrint) {
            consoleSpaces(5);
        }
        if (!quitMenu) {
            paintMenu();
        }
    }

    /**
     * Esci dal menu.
     */
    public void quit() {
        quitMenu = true;
    }

    /**
     * Mostra il menù e ritorna il valore dell'esecuzione.
     *
     * @return Il valore ritornato dall'opzione chiamata.
     */
    public P showAndWait() {
        GeneralFormatter.printOut(title, true, false);
        for (int i = 0; i < menu.size(); i++) {
            GeneralFormatter.printOut("[" + (i + 1) + "] " + menu.get(i).getKey(), true, false);
        }
        System.out.println();
        GeneralFormatter.incrementIndents();
        Optional<Integer> op;
        do {
            op = ConsoleInput.getInstance().readInteger(menu_bundle.getString("oper"), false, null);
        } while (op.get() < 1 || op.get() > menu.size());
        GeneralFormatter.decrementIndents();
        if (autoPrint) {
            consoleSpaces(3);
        }
        return executeAt(op.get() - 1);
    }

    /**
     * Fa eseguire una {@link FutureAction} associata ad una voce del menù.
     *
     * @param index Indice selezionato.
     * @return Il valore dell'esecuzione.
     */
    private P executeAt(int index) {
        Pair<String, FutureAction<P>> p = menu.get(index);
        to_execute_later.forEach((f) -> f.onSelected());
        to_execute_later.clear();
        return p.getValue().onSelected();
    }
}
