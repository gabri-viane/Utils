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
package ttt.utils.console.input.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ttt.utils.console.input.ObjectInputEngine;

/**
 * Segna un metodo o una variabile come impostabile tramite l'engine
 * {@link ObjectInputEngine}.
 *
 * @author TTT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface InputElement {

    /**
     * Il nome dell'elemento che verrà chiesto in console.
     *
     * @return Il nome da mostrare.
     */
    public String Name();

    /**
     * La classe del valore da impostare al metodo o all'attributo.
     *
     * @return La classe da generare.
     */
    public Class Type();

    /**
     * Impostare un valore diverso da {@code ""} per abilitare l'utente ad
     * evitare l'immissione di un dato e passare un valore {@code null}.
     *
     * @return La stringa che permette all'utente di saltare l'inserimento.
     */
    public String SkippableKeyword() default "";
}
