package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTConditionTrap extends ASTConditionExpression {
	private String name;
	private ASTExpression exp;
	private boolean contains;

	public ASTConditionTrap(Token location, String name, ASTExpression exp, boolean contains) {
		super(location);
		this.name = name;
		this.exp = exp;
		this.contains = contains;
	}

	public String getName() {
		return name;
	}
	
	public ASTExpression getExp() {
		return exp;
	}
	
	public boolean isContains() {
		return contains;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[");
		builder.append(exp);
		builder.append(",");
		builder.append(contains);
		builder.append("]");
		return builder.toString();
	}

	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}