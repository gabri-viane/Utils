/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import net.vnleng.utils.input.InputData;
import net.vnleng.utils.menu.utils.FutureMenuAction;
import net.vnleng.utils.menu.utils.Pair;
import net.vnleng.utils.output.GeneralFormatter;

/**
 *
 * @author gabri
 */
public abstract class Menu {

    private static final ResourceBundle menu_bundle = ResourceBundle.getBundle("net/vnleng/utils/resources/i18n/menu/menu_bundle");

    private final ArrayList<Pair<String, FutureMenuAction>> menu = new ArrayList<>();
    private final ArrayList<FutureMenuAction> to_execute_later = new ArrayList<>();

    private final String titolo;

    public Menu(String titolo) {
        this.titolo = titolo;
        init();
    }

    public void reset() {
        menu.clear();
        to_execute_later.clear();
        addOption(menu_bundle.getString("exit"), () -> {
            System.exit(0);
        });
    }

    private void init() {
        GeneralFormatter.printOut(titolo, true, false);
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
     * @param indice
     */
    public void removeOption(int indice) {
        if (indice > 0 && indice <= menu.size()) {
            menu.remove(indice - 1);
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
        GeneralFormatter.printOut(menu_bundle.getString("menu_title"), true, false);
        for (int i = 0; i < menu.size(); i++) {
            GeneralFormatter.printOut("[" + (i + 1) + "] " + menu.get(i).getKey(), true, false);
        }
        System.out.println();
        waitAnswer();
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
            op = InputData.getIstance().readInteger(menu_bundle.getString("oper"), false, null);
        } while (op.get() < 1 || op.get() > menu.size());
        GeneralFormatter.decrementIndents();
        consoleSpaces(3);
        executeAt(op.get() - 1);
        consoleSpaces(5);
        paintMenu();
    }

    /**
     * Fa eseguire una {@link FutureMenuAction} associata ad una voce del menù.
     *
     * @param index Indice selezionato.
     */
    private void executeAt(int index) {
        Pair<String, FutureMenuAction> p = menu.get(index);
        p.getValue().onSelected();
        to_execute_later.forEach((f) -> f.onSelected());
        to_execute_later.clear();
    }
}
