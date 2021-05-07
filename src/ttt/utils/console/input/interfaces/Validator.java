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
package ttt.utils.console.input.interfaces;

/**
 *
 * @author TTT
 * @param <T> Il tipo di valoreda controllare
 */
public interface Validator<T> {

    /**
     * Il metodo deve controllare il valore e nel caso quest'ultimo non rispetta
     * le regole imposte bisogna lanciare un'errore di tipo
     * {@link IllegalArgumentException}, altrimenti non è necessario ritornare
     * alcun valore.
     *
     * @param value Il valore inserito da controllare.
     * @throws IllegalArgumentException Nel caso in cui il valore non è
     * corretto.
     */
    public void validate(T value) throws IllegalArgumentException;
}
