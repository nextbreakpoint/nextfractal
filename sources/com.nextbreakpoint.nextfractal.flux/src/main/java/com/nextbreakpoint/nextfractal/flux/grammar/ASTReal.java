package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTReal extends ASTRealExpression {
	private double value;

	public ASTReal(Token location, double value) {
		super(location);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	@Override
	public void compile(StringBuilder builder) {
		builder.append(value);
	}
}