package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Token location;

	public ASTException(String message, Token location, Throwable cause) {
		super(message, cause);
		this.location = location;
	}

	public ASTException(String message, Token location) {
		super(message);
		this.location = location;
	}

	public Token getLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		return "[" + location.getLine() + ":" + location.getCharPositionInLine() + "] " + super.toString();
	}
}
