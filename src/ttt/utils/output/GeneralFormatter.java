/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt.utils.output;

/**
 *
 * @author TTT
 */
public class GeneralFormatter {

    private static int INDENTS = 0;

    public static void incrementIndents() {
        INDENTS++;
    }

    public static void decrementIndents() {
        if (INDENTS > 0) {
            INDENTS--;
        }
    }

    public static String getIndentes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < INDENTS; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    public static void printOut(String message, boolean newline, boolean error) {
        String msg = getIndentes() + message + (newline ? "\n" : "");
        if (error) {
            System.err.print(msg);
        } else {
            System.out.print(msg);
        }
    }
}
