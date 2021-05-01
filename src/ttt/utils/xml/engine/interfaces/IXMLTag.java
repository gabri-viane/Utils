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
package ttt.utils.xml.engine.interfaces;

/**
 * Interfaccia che deve essere implmentata dalle classi che rappresentano
 * un'attributo (un tag) di un elemento XML.
 *
 * @author TTT
 */
public interface IXMLTag {

    /*
     Indica una tag di un elemento XML che può essere più complessa
    <elemento tag=valore/>
    Bisogna definire i metodi base di get e set.
     */
    /**
     * Imposta il nome della Tag.
     *
     * @param name Il nome dell'attributo.
     */
    public void setName(String name);

    /**
     * Imposta il valore della Tag
     *
     * @param value Il valore dell'attributo.
     */
    public void setValue(String value);

    /**
     * Ritorna il valore associato alla Tag
     *
     * @return Il nome dell'attributo.
     */
    public String getValue();

    /**
     * Ritorna il nome della Tag.
     *
     * @return Il valore dell'attributo.
     */
    public String getName();

}
