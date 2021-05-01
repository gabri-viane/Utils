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
import ttt.utils.engines.enums.MethodType;

/**
 * Serve alle classi {@code Engine} per stabilire un metodo a cosa serve. In
 * base al tipo imposto ({@link MethodType}) il motore che si occupa del
 * completamento dei dati si comporterà in modo diverso.
 *
 * @author TTT
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EngineMethod {

    /**
     * Il tipo di metodo su cui viene posta quest'annotazione
     *
     * @return {@link MethodType}.
     */
    public MethodType MethodType();
}
