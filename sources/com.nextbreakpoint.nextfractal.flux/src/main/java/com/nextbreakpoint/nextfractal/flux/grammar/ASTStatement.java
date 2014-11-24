package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTStatement extends ASTObject {
	private String name;
	private ASTComplexExpression exp;

	public ASTStatement(Token location, String name, ASTComplexExpression exp) {
		super(location);
		this.name = name;
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public ASTComplexExpression getExp() {
		return exp;
	}
}