package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTRealFunction extends ASTRealExpression {
	private String name;
	private ASTRealExpression[] arguments;

	public ASTRealFunction(Token location, String name, ASTRealExpression[] arguments) {
		super(location);
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public ASTRealExpression[] getArguments() {
		return arguments;
	}
}