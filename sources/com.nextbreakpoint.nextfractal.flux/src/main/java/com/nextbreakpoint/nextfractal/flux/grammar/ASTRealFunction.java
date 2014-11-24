package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTRealFunction extends ASTRealExpression {
	private String name;
	private ASTComplexExpression[] arguments;

	public ASTRealFunction(Token location, String name, ASTRealExpression[] arguments) {
		super(location);
		this.arguments = arguments;
	}

	public ASTRealFunction(Token location, String name, ASTComplexExpression[] arguments) {
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