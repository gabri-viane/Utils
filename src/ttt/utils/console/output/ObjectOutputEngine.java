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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttt.utils.console.output.annotations.Printable;
import ttt.utils.console.output.interfaces.PrintableObject;

/**
 * Classe che permette tramite l'utilizzo dell'interfaccia
 * {@link PrintableObject} e l'annotazione {@link Printable} di stampare in
 * output tramite una stringa specifica uno o più oggetti.
 *
 * @author TTT
 */
public class ObjectOutputEngine {

    private ObjectOutputEngine() {

    }

    /**
     * Permette tramite una stringa specificata di stampare dei valori di
     * un'oggetto. Nel caso non vengano trovati dei valori le parti precedute da
     * '%' vengono eliminate, a differenza del metodo {@link ObjectOutputEngine#printAll(java.lang.String, java.lang.Object[])
     * }.
     *
     * @param format La stringa che contiene i valori preceduti da '%' in cui
     * verranno rimpiazzati con i valori specificati delle annotazioni
     * {@link Printable}.
     * @param o L'oggetto da utilizzare per trovare i valori.
     * @return La stringa con i valori sostituiti.
     */
    public static String printExclusive(String format, Object o) {
        return ObjectOutputEngine.print(format, o).replaceAll("(%([^\\W]++))", "");
    }

    /**
     * Permette tramite una stringa specificata di stampare dei valori degli
     * oggetti. Nel caso non vengano trovati dei valori le parti precedute da
     * '%' vengono eliminate, a differenza del metodo {@link ObjectOutputEngine#printAll(java.lang.String, java.lang.Object[])
     * }.
     *
     * @param format La stringa che contiene i valori preceduti da '%' in cui
     * verranno rimpiazzati con i valori specificati delle annotazioni
     * {@link Printable}.
     * @param os Gli oggetti da utilizzare per trovare i valori.
     * @return La stringa con i valori sostituiti.
     */
    public static String printExclusive(String format, Object... os) {
        return printAll(format, os).replaceAll("(%([^\\W]++))", "");
    }

    /**
     * Permette tramite una stringa specificata di stampare dei valori degli
     * oggetti.
     *
     * @param format La stringa che contiene i valori preceduti da '%' in cui
     * verranno rimpiazzati con i valori specificati delle annotazioni
     * {@link Printable}.
     * @param os Gli oggetti da utilizzare per trovare i valori.
     * @return La stringa con i valori sostituiti.
     */
    public static String printAll(String format, Object... os) {
        for (Object o : os) {
            format = ObjectOutputEngine.print(format, o);
        }
        return format;
    }

    /**
     * Rimpiazza i valori preceduti da '%' con gli attributi o le chiamate dei
     * metodi.
     *
     * @param format La stringa in cui sostituire i valori.
     * @param o L'oggetto da cui prendere i valori.
     * @return La stringa modificata.
     */
    public static String print(String format, Object o) {
        Class c = o.getClass();
        if (o instanceof PrintableObject) {
            for (Field f : c.getFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Printable.class)) {
                    try {
                        Printable annotation = f.getAnnotation(Printable.class);
                        Object retn = f.get(o);
                        if (retn instanceof PrintableObject) {
                            format = ObjectOutputEngine.print(format, retn);
                        } else {
                            if (annotation.replace() != null && !"".equals(annotation.replace().trim())) {
                                format = format.replaceFirst("%" + annotation.replace(), retn != null ? retn.toString() : "null");
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(ObjectOutputEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            for (Method m : c.getMethods()) {
                m.setAccessible(true);
                if (m.isAnnotationPresent(Printable.class)) {
                    Printable annotation = m.getAnnotation(Printable.class);
                    if (m.getParameterCount() == 0) {
                        try {
                            Object retn = m.invoke(o, new Object[0]);
                            if (retn instanceof PrintableObject) {
                                format = ObjectOutputEngine.print(format, retn);
                            } else {
                                if (annotation.replace() != null && !"".equals(annotation.replace().trim())) {
                                    format = format.replaceFirst("%" + annotation.replace(), retn != null ? retn.toString() : "null");
                                }
                            }
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(ObjectOutputEngine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            return format;
        }
        return o.toString();
    }

}
