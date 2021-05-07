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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ttt.utils.ProjectSettings;
import ttt.utils.console.input.annotations.InputElement;
import ttt.utils.console.input.annotations.Order;
import ttt.utils.console.input.interfaces.InputObject;
import ttt.utils.console.output.GeneralFormatter;
import ttt.utils.engines.utils.EngineUtils;
import ttt.utils.console.menu.utils.FutureAction;
import ttt.utils.console.menu.utils.Pair;

/**
 * Gestisce l'inserimento in modo automatico di un'oggetto che implementa
 * l'interfaccia {@link InputObject}.
 *
 * @author TTT
 */
public class ObjectInputEngine {

    private ObjectInputEngine() {

    }

    private static Object readValue(Class clazz, String var_name, String skippable) {
        ConsoleInput IN = ConsoleInput.getInstance();
        if (EngineUtils.isPrimitive(clazz)) {
            clazz = EngineUtils.boxPrimitiveClass(clazz);
        }
        String question = (ProjectSettings.PROGRAM_DEFAULT_OBJECT_OUTPUT_PHRASE ? "Inserire il valore per \"" : "") + var_name + (ProjectSettings.PROGRAM_DEFAULT_OBJECT_OUTPUT_PHRASE ? "\"" : "") + (ProjectSettings.PROGRAM_SHOW_VARIABLE_INPUT_TYPE_OUTPUT ? " [" + clazz.getSimpleName() + "]" : "") + ": ";
        boolean skp = skippable != null && !skippable.trim().equals("");
        question += skp ? "('" + skippable + "' per saltare)" : "";
        Object return_value = null;
        if (clazz == Integer.class) {
            return_value = !skp ? IN.readInteger(question) : IN.readInteger(question, true, skippable).orElse(null);
        } else if (clazz == Long.class) {
            return_value = !skp ? IN.readLong(question) : IN.readLong(question, true, skippable).orElse(null);
        } else if (clazz == Character.class) {
            return_value = !skp ? IN.readCharacter(question) : IN.readCharacter(question, true, skippable).orElse(null);
        } else if (clazz == Boolean.class) {
            question = "\"" + var_name + "\"? ";
            return_value = IN.readBoolean(question);
        } else if (clazz == Float.class) {
            return_value = !skp ? IN.readFloat(question) : IN.readFloat(question, true, skippable).orElse(null);
        } else if (clazz == Double.class) {
            return_value = !skp ? IN.readDouble(question) : IN.readDouble(question, true, skippable).orElse(null);
        } else if (clazz == String.class) {
            return_value = !skp ? IN.readString(question) : IN.readString(question, true, skippable).orElse(null);
        }
        return return_value;
    }

    private static boolean implementsInputObject(Class c) {
        for (Class clz : c.getInterfaces()) {
            if (clz == InputObject.class) {
                return true;
            }
        }
        return false;
    }

    /**
     * Crea una nuova istanza della classe specificata (che deve implementare
     * {@link InputObject})chiedendo all'utente di inserire i valori (dei metodi
     * o degli attributi) segnati tramite l'annotazione {@link InputElement} e
     * li chiede ordinandoli secondo i valori specificati dall'annotazione
     * {@link Order} (solo se presente, non è necessaria).
     *
     * @param <T> La classe da istanziare che deve implementare
     * {@link InputObject}.
     * @param object La classe da istanziare che deve implementare
     * {@link InputObject}.
     * @param nome_elemento Il nome della classe che viene chiesta in input
     * all'utente.
     * @return L'istanza della nuova classe, solo dopo aver chiesto all'utente i
     * dati necessari.
     */
    public static <T extends InputObject> T readNewObject(Class<T> object, String nome_elemento) {
        if (object != null) {
            try {
                Constructor<? extends InputObject> constructor = object.getConstructor();
                if (constructor != null) {
                    GeneralFormatter.printOut((ProjectSettings.PROGRAM_DEFAULT_OBJECT_OUTPUT_PHRASE ? "Inserirmento di \"" : "") + nome_elemento + (ProjectSettings.PROGRAM_DEFAULT_OBJECT_OUTPUT_PHRASE ? "\"" : "") + (ProjectSettings.PROGRAM_SHOW_OBJECT_INPUT ? " [Oggetto] " : "") + ":", true, false);
                    GeneralFormatter.incrementIndents();
                    InputObject newInstance = constructor.newInstance();
                    Method[] metodi = object.getDeclaredMethods();

                    ArrayList<Pair<Integer, FutureAction<Void>>> metodi_chiamabili = new ArrayList<>();
                    ArrayList<Pair<Integer, FutureAction<Void>>> elementi_chiamabili = new ArrayList<>();

                    for (Method metodo : metodi) {
                        metodo.setAccessible(true);
                        Order ordine = metodo.getAnnotation(Order.class);
                        FutureAction met = () -> {
                            try {
                                InputElement annotazione = metodo.getDeclaredAnnotation(InputElement.class);
                                if (annotazione != null) {
                                    Object read_val;
                                    if (implementsInputObject(annotazione.Type())) {
                                        GeneralFormatter.incrementIndents();
                                        read_val = readNewObject(annotazione.Type(), annotazione.Name());
                                        GeneralFormatter.decrementIndents();
                                    } else {
                                        read_val = readValue(annotazione.Type(), annotazione.Name(), annotazione.SkippableKeyword());
                                    }
                                    if (metodo.getParameterCount() == 1 && (read_val != null ? metodo.getParameterTypes()[0] == read_val.getClass() : true)) {
                                        metodo.invoke(newInstance, read_val);
                                    }
                                }
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                Logger.getLogger(ObjectInputEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return null;
                        };
                        if (ordine != null) {
                            metodi_chiamabili.add(new Pair(ordine.Priority(), met));
                        } else {
                            metodi_chiamabili.add(new Pair(null, met));
                        }
                    }
                    Field[] attributi = object.getDeclaredFields();
                    for (Field attributo : attributi) {
                        attributo.setAccessible(true);
                        Order ordine = attributo.getAnnotation(Order.class);
                        FutureAction met = () -> {
                            try {
                                InputElement annotazione = attributo.getDeclaredAnnotation(InputElement.class);
                                if (annotazione != null) {
                                    Object read_val;
                                    if (implementsInputObject(annotazione.Type())) {
                                        GeneralFormatter.incrementIndents();
                                        read_val = readNewObject(annotazione.Type(), annotazione.Name());
                                        GeneralFormatter.decrementIndents();
                                    } else {
                                        read_val = readValue(annotazione.Type(), annotazione.Name(), annotazione.SkippableKeyword());
                                    }
                                    if ((read_val != null && EngineUtils.isPrimitive(attributo.getType()) ? EngineUtils.boxPrimitiveClass(attributo.getType()) : attributo.getType()) == read_val.getClass()) {
                                        attributo.set(newInstance, read_val);
                                    }
                                }
                            } catch (IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(ObjectInputEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return null;
                        };
                        if (ordine != null) {
                            elementi_chiamabili.add(new Pair(ordine.Priority(), met));
                        } else {
                            elementi_chiamabili.add(new Pair(null, met));
                        }
                    }
                    elementi_chiamabili.stream().sorted((o1, o2) -> {
                        return o1.getKey() != null && o2.getKey() != null ? o1.getKey().compareTo(o2.getKey()) : -1;
                    }).forEachOrdered((t) -> {
                        t.getValue().onSelected();
                    });
                    metodi_chiamabili.stream().sorted((o1, o2) -> {
                        return o1.getKey() != null && o2.getKey() != null ? o1.getKey().compareTo(o2.getKey()) : -1;
                    }).forEachOrdered((t) -> {
                        t.getValue().onSelected();
                    });
                    GeneralFormatter.decrementIndents();
                    return object.cast(newInstance);
                }
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ObjectInputEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
