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
public class Obj2 implements PrintableObject {

    @Printable(replace = "%TT3")
    public String theThird = "Value";

    @Printable(replace = "%Falaffel")
    public String theFourth = "JSON";

    @Printable(replace = "%TT3")
    public String Jakson = "Jakson";
}
