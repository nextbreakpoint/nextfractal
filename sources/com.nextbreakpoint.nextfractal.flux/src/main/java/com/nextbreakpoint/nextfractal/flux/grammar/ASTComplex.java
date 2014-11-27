package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.core.math.Complex;

public class ASTComplex extends ASTComplexExpression {
	private final Complex value;

	public ASTComplex(Token location, Complex value) {
		super(location);
		this.value = value;
	}

	public ASTComplex(Token location, double r, double i) {
		super(location);
		this.value = new Complex(r, i);
	}

	public ASTComplex(ASTReal real) {
		super(real.getLocation());
		this.value = new Complex(real.getValue(), 0);
	}

	public Complex getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void compile(StringBuilder builder) {
		builder.append("new Complex(");
		builder.append(value.r);
		builder.append(",");
		builder.append(value.i);
		builder.append(")");
	}
}