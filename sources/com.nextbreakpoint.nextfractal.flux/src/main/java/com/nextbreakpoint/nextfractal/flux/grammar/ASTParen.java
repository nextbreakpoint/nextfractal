package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTParen extends ASTExpression {
	private ASTExpression exp;

	public ASTParen(Token location, ASTExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTExpression getExp() {
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

	public boolean isReal() {
		return exp.isReal();
	}
}