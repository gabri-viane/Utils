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
package ttt.utils.xml.document.structure.exception;

/**
 * Eccezzione che rappresenta un documento invalido secondo una struttura.
 *
 * @author TTT
 */
public class InvalidXMLFormat extends RuntimeException {

    public InvalidXMLFormat(String message) {
        super(message);
    }

}
