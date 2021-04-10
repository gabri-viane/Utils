/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import net.vnleng.utils.output.ObjectPrinter;
import test.objs.OggettoTest;

/**
 *
 * @author gabri
 */
public class input {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //InputData.getIstance().readInteger("Inserisci valore:", true, "*");
        ObjectPrinter op = new ObjectPrinter();
        System.out.println(op.print("%V2 (%V1) %TT3", new OggettoTest()));
    }

}
