package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

public class Logger {
    public void error(String message, Token location) {
        //TODO completare con location
        System.out.println(message);
    }

    public void warning(String message, Token location) {
//        "[" + location.getLine() + ":" + location.getCharPositionInLine() + "]
        //TODO completare con location
        System.out.println(message);
    }

    public void info(String message, Token location) {
        //TODO completare con location
        System.out.println(message);
    }

    public void fail(String message, Token location) {
        //TODO completare con location
        System.err.println(message);
        throw new RuntimeException(message);
    }
}
