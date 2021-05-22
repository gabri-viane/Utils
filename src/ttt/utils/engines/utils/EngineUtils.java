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
package ttt.utils.engines.utils;

/**
 * Implementa i metodi comuni utili per le classi di tipo Engine.
 *
 * @author TTT
 */
public final class EngineUtils {

    /**
     * Controlla se una classe fa parte dei tipi primitivi.
     *
     * @param type La classe da controllare.
     * @return {@code true} se è una classe primitiva.
     */
    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    /**
     * Controlla se una classe fa parte dei boxed-type.
     *
     * @param type La classe da controllare.
     * @return {@code true} se è una classe boxed.
     */
    public static boolean isBoxed(Class<?> type) {
        return (type == Integer.class || type == Long.class || type == Double.class || type == Float.class
                || type == Boolean.class || type == Byte.class || type == Character.class || type == Short.class);
    }

    /**
     * Esegue il boxing di una classe primitiva.
     *
     * @param type La classe per cui eseguire il boxing.
     * @return La nuova classe di autoboxing.
     */
    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

    public static Object convertStringToPrimitive(String value, Class<?> type) {
        if (value != null) {
            if (isPrimitive(type)) {
                type = boxPrimitiveClass(type);
            }
            if (type == Integer.class) {
                return Integer.parseInt(value);
            } else if (type == Long.class) {
                return Long.parseLong(value);
            } else if (type == Double.class) {
                return Double.parseDouble(value);
            } else if (type == Float.class) {
                return Float.parseFloat(value);
            } else if (type == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (type == Byte.class) {
                return Byte.class;
            } else if (type == Character.class) {
                return value.charAt(0);
            } else if (type == Short.class) {
                return Short.parseShort(value);
            } else {
                String string = "class '" + type.getName() + "' is not a primitive: expected primitive, cannot convert string to: " + type.getName();
                throw new IllegalArgumentException(string);
            }
        } else {
            String string = "Null value cannot be converted to a primitive type.";
            throw new IllegalArgumentException(string);
        }
    }
}
