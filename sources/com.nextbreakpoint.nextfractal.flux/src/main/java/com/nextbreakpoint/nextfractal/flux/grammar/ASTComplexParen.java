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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(exp);
		builder.append(")");
		return builder.toString();
	}
	
	@Override
	public void compile(StringBuilder builder) {
		exp.compile(builder);
	}
}