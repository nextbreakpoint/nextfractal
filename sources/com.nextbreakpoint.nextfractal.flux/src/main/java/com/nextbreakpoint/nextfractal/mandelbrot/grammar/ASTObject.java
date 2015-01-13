package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTObject {
	protected Token location;

	public ASTObject(Token location) {
		this.location = location;
	}

	public Token getLocation() {
		return location;
	}

	public void setLocation(Token location) {
		this.location = location;
	}
}