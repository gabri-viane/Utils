/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.output;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vnleng.utils.output.annotations.Printable;
import net.vnleng.utils.output.interfaces.PrintableObject;

/**
 *
 * @author gabri
 */
public class ObjectPrinter {

    public ObjectPrinter() {

    }

    public String print(String format, Object o) {
        Class c = o.getClass();
        if (o instanceof PrintableObject) {
            for (Field f : c.getFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Printable.class)) {
                    try {
                        Printable annotation = f.getAnnotation(Printable.class);
                        Object retn = f.get(o);
                        if (retn instanceof PrintableObject) {
                            format = print(format, retn);
                        } else {
                            format = format.replaceAll(annotation.replace(), retn != null ? retn.toString() : "null");
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(ObjectPrinter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return format;
        }
        return o.toString();
    }

}
