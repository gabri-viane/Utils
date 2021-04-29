/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ttt.utils.xml.engine.enums.MethodType;

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
