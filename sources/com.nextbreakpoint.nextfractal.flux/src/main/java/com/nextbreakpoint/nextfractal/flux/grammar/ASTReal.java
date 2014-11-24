package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTReal extends ASTRealExpression {
	private double value;

	public ASTReal(Token location, double value) {
		super(location);
		this.value = value;
	}

	public ASTReal(Token location, String text) {
		super(location);
		this.value = Double.parseDouble(text);
	}

	public double getValue() {
		return value;
	}
}