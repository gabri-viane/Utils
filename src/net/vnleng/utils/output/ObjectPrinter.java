/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vnleng.utils.output;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.vnleng.utils.output.annotations.Printable;
import net.vnleng.utils.output.interfaces.PrintableObject;

/**
 *
 * @author gabri
 */
public class ObjectPrinter {

    private ObjectPrinter() {

    }

    public static String printExclusive(String format, Object o) {
        return ObjectPrinter.print(format, o).replaceAll("(%([^\\W]++))", "");
    }

    public static String printExclusive(String format, Object... os) {
        return printAll(format, os).replaceAll("(%([^\\W]++))", "");
    }

    public static String printAll(String format, Object... os) {
        for (Object o : os) {
            format = ObjectPrinter.print(format, o);
        }
        return format;
    }

    public static String print(String format, Object o) {
        Class c = o.getClass();
        if (o instanceof PrintableObject) {
            for (Field f : c.getFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Printable.class)) {
                    try {
                        Printable annotation = f.getAnnotation(Printable.class);
                        Object retn = f.get(o);
                        if (retn instanceof PrintableObject) {
                            format = ObjectPrinter.print(format, retn);
                        } else {
                            if (annotation.replace() != null && !"".equals(annotation.replace().trim())) {
                                format = format.replaceAll("%" + annotation.replace(), retn != null ? retn.toString() : "null");
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(ObjectPrinter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            for (Method m : c.getMethods()) {
                m.setAccessible(true);
                if (m.isAnnotationPresent(Printable.class)) {
                    Printable annotation = m.getAnnotation(Printable.class);
                    if (m.getParameterCount() == 0) {
                        try {
                            Object retn = m.invoke(o, new Object[0]);
                            if (retn instanceof PrintableObject) {
                                format = ObjectPrinter.print(format, retn);
                            } else {
                                if (annotation.replace() != null && !"".equals(annotation.replace().trim())) {
                                    format = format.replaceAll("%" + annotation.replace(), retn != null ? retn.toString() : "null");
                                }
                            }
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(ObjectPrinter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            return format;
        }
        return o.toString();
    }

}
