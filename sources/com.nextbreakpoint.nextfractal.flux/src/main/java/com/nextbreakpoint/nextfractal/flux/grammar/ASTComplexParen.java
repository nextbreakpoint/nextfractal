package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTComplexParen extends ASTComplexExpression {
	private ASTComplexExpression exp;

	public ASTComplexParen(Token location, ASTComplexExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTComplexExpression getExp() {
		return exp;
	}
}