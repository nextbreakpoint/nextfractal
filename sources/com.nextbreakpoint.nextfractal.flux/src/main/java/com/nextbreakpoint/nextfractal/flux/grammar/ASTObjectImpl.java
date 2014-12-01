package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTObjectImpl implements ASTObject {
	protected Token location;

	public ASTObjectImpl(Token location) {
		this.location = location;
	}

	public Token getLocation() {
		return location;
	}

	public void setLocation(Token location) {
		this.location = location;
	}
}