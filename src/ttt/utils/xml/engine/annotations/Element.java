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
package ttt.utils.xml.engine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ttt.utils.xml.engine.XMLEngine;
import ttt.utils.xml.engine.interfaces.IXMLTag;

/**
 * Se anteposto alla definizione di un metodo o variabile permette alla classe
 * {@link XMLEngine} di valutarlo come Elemento XML.
 *
 * @author TTT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Element {

    /**
     * Il nome dell'Elemento. Deve corrispondere con il nome di un elemento del
     * file XML per poter essere gestito correttamente.
     *
     * @return Il nome dell'elemento
     */
    public String Name();

    /**
     * Se può avere delle tags nella sua definizione. ({@link IXMLTag}) Il
     * valore d default è {@code true}.
     *
     * @return {@code true} se la classe {@link XMLEngine} deve tenere conto di
     * eventuali attributi.
     */
    public boolean CanHaveTags() default true;

    /**
     * Se può avere un valore associato alla sua definizione. Il valore d
     * default è {@code true}.
     *
     * @return {@code true} se la classe {@link XMLEngine} deve tenere conto di
     * un eventuale valore associato.
     */
    public boolean CanHaveValue() default true;

}
