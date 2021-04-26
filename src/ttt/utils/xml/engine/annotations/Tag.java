/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.xml.engine.annotations;

/**
 * Tag detto anche Attributo.
 *
 * @author TTT
 */
public @interface Tag {

    /**
     * Il nome del Tag(Attributo).
     *
     * @return
     */
    public String Name();

    /**
     * Tipo di valore contenuto. Di default è pari a {@code String.class }.
     *
     * @return Classe del valore ottenuto.
     */
    public Class Type() default String.class;
}
