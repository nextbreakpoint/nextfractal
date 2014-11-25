package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTComplexAng extends ASTRealExpression {
	private ASTComplexExpression exp;

	public ASTComplexAng(Token location, ASTComplexExpression exp) {
		super(location);
		this.exp = exp;
	}
	
	public ASTComplexExpression getExp() {
		return exp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ang");
		builder.append("(");
		builder.append(exp);
		builder.append(")");
		return builder.toString();
	}
}