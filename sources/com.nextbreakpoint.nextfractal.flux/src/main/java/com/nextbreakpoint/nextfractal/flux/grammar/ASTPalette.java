package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTPalette extends ASTObject {
	private String name;

	public ASTPalette(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}