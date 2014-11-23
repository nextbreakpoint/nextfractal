package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTRealVariable extends ASTRealExpression {
	private String name;

	public ASTRealVariable(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}