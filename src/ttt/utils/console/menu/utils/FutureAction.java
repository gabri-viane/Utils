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
package ttt.utils.console.menu.utils;

/**
 * Permette di eseguire il codice nel metodo {@link FutureAction#onSelected() }
 * in futuro.
 *
 * @author TTT
 * @param <P> Il valore che ritorna all'esecuzione del metodo.
 */
@FunctionalInterface
public interface FutureAction<P> {

    public P onSelected();
}
