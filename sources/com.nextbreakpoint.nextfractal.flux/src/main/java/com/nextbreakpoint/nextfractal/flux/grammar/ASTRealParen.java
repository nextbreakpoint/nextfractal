package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRealParen extends ASTRealExpression {
	private ASTRealExpression exp;

	public ASTRealParen(Token location, ASTRealExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTRealExpression getExp() {
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
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}