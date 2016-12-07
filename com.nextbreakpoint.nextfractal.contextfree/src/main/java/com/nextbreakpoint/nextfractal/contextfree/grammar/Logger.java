package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

import java.util.logging.Level;

public class Logger {
    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());

    public void error(String message, Token location) {
        logger.log(Level.WARNING, message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }

    public void warning(String message, Token location) {
        logger.log(Level.WARNING, message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }

    public void info(String message, Token location) {
        logger.log(Level.INFO, message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }

    public void fail(String message, Token location) {
        logger.log(Level.WARNING, message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
        throw new RuntimeException(message + (location != null ? " [" + location.getLine() + ":" + location.getCharPositionInLine() + "]" : ""));
    }
}
