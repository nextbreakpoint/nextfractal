package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTConditionTrap extends ASTConditionExpression {
	private String name;
	private ASTComplexExpression exp;

	public ASTConditionTrap(Token location, String name, ASTComplexExpression exp) {
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[");
		builder.append(exp);
		builder.append("]");
		return builder.toString();
	}
}