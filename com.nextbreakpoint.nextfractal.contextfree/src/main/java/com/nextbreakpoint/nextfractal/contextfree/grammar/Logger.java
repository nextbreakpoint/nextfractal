package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

public class Logger {
    public void error(String message, Token location) {
        System.out.println(message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }

    public void warning(String message, Token location) {
        System.out.println(message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }

    public void info(String message, Token location) {
        System.out.println(message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }

    public void fail(String message, Token location) {
        System.err.println(message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
        throw new RuntimeException(message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }
}
