package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRealVariable extends ASTRealExpression {
	private String name;

	public ASTRealVariable(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}