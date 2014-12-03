package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTColorPalette extends ASTColorExpression {
	private String name;
	private ASTExpression exp;
	
	public ASTColorPalette(Token location, String name, ASTExpression exp) {
		super(location);
		this.name = name;
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public ASTExpression getExp() {
		return exp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[");
		builder.append(exp);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}
