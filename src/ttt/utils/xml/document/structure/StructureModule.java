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
package ttt.utils.xml.document.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import ttt.utils.xml.document.structure.rules.Rules;
import ttt.utils.xml.engine.interfaces.IXMLElement;

/**
 * Modulo utilizzato nelle strutture XML per poter verificare la correttezza di
 * un file XML.
 *
 * @author TTT
 */
public class StructureModule {

    private static final ResourceBundle read_errors = ResourceBundle.getBundle("ttt/utils/resources/i18n/xml/read_errors");

    private final HashMap<Class<? extends IXMLElement>, StructureModule> structure = new HashMap<>();
    private final Class<? extends IXMLElement> clazz;
    private final Rules rule;
    private String last_error = "";

    /**
     * Crea un nuovo modulo di verifica.
     *
     * @param clazz La classe per cui verificare la correttezza.
     * @param rule Il tipo di regola da seguire per il controllo.
     */
    public StructureModule(Class<? extends IXMLElement> clazz, Rules rule) {
        this.clazz = clazz;
        this.rule = rule;
    }

    /**
     * Aggiunge un sotto-modulo di verifica.
     *
     * @param m Il modulo che permette le verifica.
     * @return Questo modulo in modo da poter concatenare l'aggiunta in maniera
     * agevole.
     */
    public StructureModule addModule(StructureModule m) {
        structure.put(m.clazz, m);
        return this;
    }

    /**
     * Rimuovi un sotto-modulo di verifica.
     *
     * @param m Il modulo da rimuovere.
     */
    public void removeModule(StructureModule m) {
        structure.remove(m.clazz);
    }

    /**
     * Ritrorna il nome del modulo nel caso di un fallimento nella verifica di
     * un documento.
     *
     * @return L'ultimo errore scatenato.
     */
    public String getLastError() {
        return last_error;
    }

    /**
     * Metodo che si occupa di validare una lista di elementi.
     *
     * @param struct La lisa da verificare.
     * @return {@code true} solamente nel caso in cui ogni modulo ed ogni suo
     * sottomodulo sono verificati correttamente.
     */
    protected boolean validate(List<IXMLElement> struct) {
        Stream<IXMLElement> filter = struct.stream().filter(e -> {
            return e.getClass().equals(clazz);
        });
        long count = 0;
        Iterator<IXMLElement> iterator = filter.iterator();
        while (iterator.hasNext()) {
            List<IXMLElement> els = iterator.next().getElements();
            if (!structure.values().stream().noneMatch(sm -> {
                boolean ver = !sm.validate(els);
                if (ver) {
                    last_error = sm.last_error;
                }
                return ver;
            })) {
                return false;
            }
            count++;
        }
        boolean valid = rule.valid(count);
        if (!valid) {
            last_error = clazz.getName() + read_errors.getString("fail");
        }
        return valid;
    }

}
