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
package ttt.utils.xml.engine;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttt.utils.engines.enums.FieldType;
import ttt.utils.engines.enums.MethodType;
import ttt.utils.engines.interfaces.EngineField;
import ttt.utils.engines.interfaces.EngineMethod;
import ttt.utils.xml.engine.annotations.Element;
import ttt.utils.xml.engine.annotations.Tag;
import ttt.utils.xml.engine.interfaces.IXMLElement;
import ttt.utils.xml.engine.interfaces.IXMLTag;

/**
 *
 * @author TTT
 */
public class XMLWriteSupportEngine {

    private final IXMLElement element;

    public XMLWriteSupportEngine(IXMLElement element) {
        this.element = element;
    }

    /**
     * Applica i cambiamenti di un elemento alla sorgente. Ad esempio le tag
     * cambiate devono essere applicate prima di poter essere scritte.
     */
    public void applyChanges() {
        transferChanges(element);
    }

    private void transferChanges(IXMLElement exec) {
        Class c = exec.getClass();
        Element main_ann = XMLEngine.getAnnotationFrom(c);
        if (main_ann != null && main_ann.CanHaveTags()) {
            for (Method m : c.getDeclaredMethods()) {
                EngineMethod meta = m.getAnnotation(EngineMethod.class);//Il metodo deve essre annotato con @EngineMethod
                if (meta != null && meta.MethodType() == MethodType.GET) {
                    Tag tag_annot = m.getAnnotation(Tag.class);
                    if (tag_annot != null && m.getParameterCount() == 0) {
                        try {
                            IXMLTag effective_tag = exec.getTag(tag_annot.Name());
                            Object res = m.invoke(exec);
                            effective_tag.setValue(res != null ? res.toString() : "");
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                EngineField meta = f.getAnnotation(EngineField.class);
                if (meta != null && main_ann.CanHaveTags() && (meta.FieldType() == FieldType.READ_AND_WRITE || meta.FieldType() == FieldType.READ)) {
                    Tag tag_annot = f.getAnnotation(Tag.class);
                    if (tag_annot != null) {
                        try {
                            IXMLTag effective_tag = exec.getTag(tag_annot.Name());
                            Object res = f.get(exec);
                            effective_tag.setValue(res != null ? res.toString() : "");
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(XMLEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        exec.getElements().forEach(to_apply -> {
            transferChanges(to_apply);
        });
    }

}
