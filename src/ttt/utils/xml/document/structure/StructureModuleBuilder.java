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
import java.util.List;
import ttt.utils.ProjectSettings;
import ttt.utils.xml.document.XMLDocument;
import ttt.utils.xml.document.structure.rules.Rules;
import ttt.utils.xml.engine.interfaces.IXMLElement;

/**
 *
 * @author TTT
 */
public class StructureModuleBuilder {

    public static StructureModule buildFromString(String build_struct, HashMap<Character, Class<? extends IXMLElement>> available_classes) {
        //Troppo complesso, lo farò magari di giorno...
        return null;
    }

    /**
     * Genera una struttura da un documento xml.
     *
     * @param document Il documento da cui estrarre la struttura di verifica.
     * @return La struttura di verifica.
     */
    public static Structure buildFromDocument(XMLDocument document) {
        Structure s = new Structure();
        s.setStructureModel(buildModulesFromDocument(document));
        return s;
    }

    /**
     * Genera tutti i moduli necessari per verificare in modo corretto il
     * documento specificato.
     *
     * @param document Documento da cui estrarre i moduli di verifica.
     * @return Il modulo di verifica per la root contenente tutti i sottomoduli.
     */
    public static StructureModule buildModulesFromDocument(XMLDocument document) {
        IXMLElement root = document.getRoot();
        StructureModule root_module = new StructureModule(root.getClass(), Rules.ONE);
        externalizeModule(root_module, root.getElements());
        return root_module;
    }

    private static void externalizeModule(StructureModule parent, List<IXMLElement> elems) {
        HashMap<Class<? extends IXMLElement>, StructureModule> ns = new HashMap<>();
        for (IXMLElement elem : elems) {
            if (!ns.containsKey(elem.getClass())) {
                StructureModule sm = new StructureModule(elem.getClass(), Rules.getAppropriate(elems.stream().filter((t) -> {
                    return t.getClass().equals(elem.getClass());
                }).count(), ProjectSettings.XML_STRUCTURE_ZERO_ALLOWED));
                ns.put(elem.getClass(), sm);
                externalizeModule(sm, elem.getElements());
                parent.addModule(sm);
            }
        }
    }

}
