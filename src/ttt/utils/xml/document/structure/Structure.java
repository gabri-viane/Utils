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

import java.util.ResourceBundle;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.document.structure.exception.InvalidXMLFormat;
import ttt.utils.xml.document.structure.exception.UninitializedMandatoryProperty;

/**
 * Rappresenta una struttura che permette di verificare la correttezza di un
 * documento XML.
 *
 * @author TTT
 */
public class Structure {

    private static final ResourceBundle read_errors = ResourceBundle.getBundle("ttt/utils/resources/i18n/xml/read_errors");

    private StructureModule module;

    /**
     * Crea una struttura vuota.
     */
    public Structure() {

    }

    /**
     * Imposta il modello di root da controllare.
     *
     * @param sm Il modello.
     */
    public void setStructureModel(StructureModule sm) {
        module = sm;
    }

    /**
     * Ritorna il modello della root.
     *
     * @return Modello principale.
     */
    public StructureModule getStructureModel() {
        return module;
    }

    /**
     * Verifica un documento XML secondo la struttura corrente.
     *
     * @param document Il documento principale.
     * @throws InvalidXMLFormat Nel caso in cui il documento non rispetti la
     * struttura definita dai modelli.
     * @throws UninitializedMandatoryProperty Nel caso in cui il modello non sia
     * stato ancora impostato.
     */
    public void verify(XMLDocument document) throws InvalidXMLFormat, UninitializedMandatoryProperty {
        if (module != null) {
            if (!module.validate(document.getElements())) {
                throw new InvalidXMLFormat(read_errors.getString("invalid_doc_struct") + module.getLastError());
            }
        } else {
            throw new UninitializedMandatoryProperty(read_errors.getString("uninit_module"));
        }
    }

}
