package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTComplexVariable extends ASTComplexExpression {
	private String name;

	public ASTComplexVariable(Token location, String name) {
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
	
	@Override
	public void compile(StringBuilder builder) {
		builder.append(name);
	}
}