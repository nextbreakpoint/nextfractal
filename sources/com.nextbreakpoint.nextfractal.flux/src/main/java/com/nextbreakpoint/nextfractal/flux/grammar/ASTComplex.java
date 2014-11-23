package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.core.math.Complex;

class ASTComplex extends ASTComplexExpression {
	private Complex value;

	public ASTComplex(Token location, Complex value) {
		super(location);
		this.value = value;
	}

	public ASTComplex(Token location, double r, double i) {
		super(location);
		this.value = new Complex(r, i);
	}

	public ASTComplex(Token location, String r, String i) {
		super(location);
		this.value = new Complex(Double.parseDouble(r), Double.parseDouble(i));
	}

	public Complex getValue() {
		return value;
	}
}