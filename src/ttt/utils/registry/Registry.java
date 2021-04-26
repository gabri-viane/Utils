/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.registry;

import java.util.HashMap;
import ttt.utils.output.GeneralFormatter;
import ttt.utils.registry.abstracts.RegistrableEntry;
import ttt.utils.registry.events.RegistryEvent;
import ttt.utils.registry.exception.RegistryException;
import ttt.utils.registry.interfaces.IndexID;

/**
 * Classe non istanziabile che permette di registrare nuovi elementi nel
 * planetario.
 *
 * @author TTT
 */
public final class Registry implements IndexID {

    private static final Registry register_key = new Registry();
    private static long currentID = 0;

    private static final HashMap<Long, RegistrableEntry> registry = new HashMap<>();
    private static final HashMap<String, Long> secondary_registry = new HashMap<>();

    private Registry() {

    }

    /**
     * Serve solo come test.
     *
     * @return <code>true</code>, sempre
     */
    public boolean onCall() {
        return true;
    }

    /**
     * Registra un nuovo elemento e gli assegna l'ID.L'oggetto deve estendere la
     * classe astratta {@link RegistrableEntry}.
     *
     * @param re L'oggetto da registrare.
     * @param entry_name Il nome con cui l'oggetto viene registrato (unico,
     * altrimenti la registrazione fallisce).
     * @return <code>true</code> se la registrazione avviene con successo,
     * altrimenti <code>false</code>.
     */
    public static boolean registerEntry(RegistrableEntry re, String entry_name) {
        try {
            if (re != null && !re.isRegistered()
                    && entry_name != null && !"".equals(entry_name.trim()) && !secondary_registry.containsKey(entry_name)) {
                re.register(currentID, register_key);
                re.setEntryName(entry_name, register_key);
                secondary_registry.put(entry_name, currentID);
                registry.put(currentID++, re);
                RegistryEvent.getInstance().elementRegistered(re, register_key);
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
    public static boolean removeEntry(RegistrableEntry re) {
        if (re != null && registry.containsValue(re)) {
            registry.remove(re.getID());
            secondary_registry.remove(re.getEntryName());
            re.remove(register_key);
            RegistryEvent.getInstance().elementRemoved(re, register_key);
            return true;
        }
        return false;
    }

    /**
     * Ritorna un {@link RegistrableEntry} dato l'ID corrispondente.
     *
     * @param ID L'ID da cercare.
     * @return {@code null} se non esiste nessun elemento con quell'ID
     * altrimenti un riferimento ad un {@link RegistrableEntry}.
     */
    public static RegistrableEntry getEntry(long ID) {
        return registry.get(ID);
    }

    /**
     * Ritorna un {@link RegistrableEntry} dato il nome di registrazione
     * corrispondente.
     *
     * @param name Il nome da cercare.
     * @return {@code null} se non esiste nessun elemento con quel nome
     * altrimenti un riferimento ad un {@link RegistrableEntry}.
     */
    public static RegistrableEntry getEntry(String name) {
        Long val_id = secondary_registry.get(name);
        return val_id != null ? registry.get(val_id) : null;
    }

    /**
     * Stampa una rappresentazione a lista del registro:
     * <p>
     * ID | {@link RegistrableEntry#toString() }
     */
    public static void printRegistry() {
        registry.forEach((t, u) -> {
            GeneralFormatter.printOut("ID: " + t + "\t\t" + u.toString(), true, false);
        });
    }

    @Override
    public long getLastID() {
        return currentID - 1;
    }

}
