/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.console.menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import ttt.utils.console.input.ConsoleInput;
import ttt.utils.console.menu.utils.FutureMenuAction;
import ttt.utils.console.menu.utils.Pair;
import ttt.utils.console.output.GeneralFormatter;

/**
 *
 * @author TTT
 * @param <P> Tipo di menu, nel caso ritorna un valore
 */
public abstract class Menu<P> {

    private static final ResourceBundle menu_bundle = ResourceBundle.getBundle("ttt/utils/resources/i18n/menu/menu_bundle");

    private final ArrayList<Pair<String, FutureMenuAction<P>>> menu = new ArrayList<>();
    private final ArrayList<FutureMenuAction<P>> to_execute_later = new ArrayList<>();

    private final String title;
    private boolean quitMenu = false;

    public Menu(String title){
        this(title, false);
    }
    
    public Menu(String title, boolean print_menu_title) {
        this.title = title;
        init(print_menu_title);
    }

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
    public void addOption(String option_message, FutureMenuAction action) {
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
     * @param action
     */
    public void addLazyExecutable(FutureMenuAction action) {
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
     * @param text
     * @return
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

    public void quit() {
        quitMenu = true;
    }

    /**
     * Mostra il menù e ritorna il valore dell'esecuzione
     *
     * @return
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
     * Fa eseguire una {@link FutureMenuAction} associata ad una voce del menù.
     *
     * @param index Indice selezionato.
     */
    private P executeAt(int index) {
        Pair<String, FutureMenuAction<P>> p = menu.get(index);
        to_execute_later.forEach((f) -> f.onSelected());
        to_execute_later.clear();
        return p.getValue().onSelected();
    }
}
