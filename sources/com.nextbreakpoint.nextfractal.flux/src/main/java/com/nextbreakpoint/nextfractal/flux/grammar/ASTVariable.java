package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTVariable extends ASTComplexExpression {
	private String name;

	public ASTVariable(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}