package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

public class Logger {
    public static void error(String message, Token location) {
        //TODO completare
        System.out.println(message);
    }

    public static void warning(String message, Token location) {
//        "[" + location.getLine() + ":" + location.getCharPositionInLine() + "]
        //TODO completare
        System.out.println(message);
    }

    public static void info(String message, Token location) {
        //TODO completare
        System.out.println(message);
    }

    public static void fail(String message, Token location) {
        //TODO completare
        System.err.println(message);
        throw new RuntimeException(message);
    }
}
