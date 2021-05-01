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

/**
 * Tag detto anche attributo.<br>
 *
 * Permette di definire una variabile o un metodo (solo se pubblici) come tag,
 * in modo tale che la classe {@link XMLEngine} possa gestire in modo corretto
 * le tag di un elemento.
 *
 * @author TTT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Tag {

    /**
     * Il nome del Tag(attributo). Deve corrispondere con l'effettivo nome del
     * tag nel file XML per poter essere caricato correttamente.
     *
     * @return Il nome dell'attributo
     */
    public String Name();

    /**
     * Tipo di valore contenuto. Di default è pari a {@code String.class }.
     *
     * @return Classe del valore ottenuto.
     */
    public Class ValueType() default String.class;
}
