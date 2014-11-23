package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTComplexVariable extends ASTComplexExpression {
	private String name;

	public ASTComplexVariable(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}