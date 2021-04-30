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
