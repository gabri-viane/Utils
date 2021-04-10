/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.objs;

import net.vnleng.utils.output.annotations.Printable;
import net.vnleng.utils.output.interfaces.PrintableObject;

/**
 *
 * @author gabri
 */
public class OggettoTest implements PrintableObject {

    public OggettoTest() {
    }

    @Printable(replace = "%V1")
    public String val = "JJ_lol";

    @Printable(replace = "%V2")
    public double valore_1 = 12.45;

    @Printable(replace = "%VObj")
    public Obj2 obj = new Obj2();

}
