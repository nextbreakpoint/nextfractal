package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTComplexFunction extends ASTComplexExpression {
	private String name;
	private ASTComplexExpression[] arguments;

	public ASTComplexFunction(Token location, String name, ASTComplexExpression[] arguments) {
		super(location);
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public ASTComplexExpression[] getArguments() {
		return arguments;
	}
}