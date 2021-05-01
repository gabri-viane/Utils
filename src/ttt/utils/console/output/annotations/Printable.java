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
package ttt.utils.console.output.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ttt.utils.console.output.ObjectOutputEngine;

/**
 * Viene posta su metodi e attributi e permette all'engine
 * {@link ObjectOutputEngine} di utilizzare il valore ritornato ddi essere
 * sostituito nella stringa da formattare.
 *
 * @author TTT
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface Printable {

    /**
     * Il valore da sostituire nella stringa di output da formattare.
     *
     * @return La stringa da sostituire (preceduta da '%').
     */
    public String replace() default "";
}
