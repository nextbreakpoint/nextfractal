package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTNumber extends ASTExpression {
	private final double r;
	private final double i;

	public ASTNumber(Token location, double r, double i) {
		super(location);
		this.r = r;
		this.i = i;
	}

	public ASTNumber(Token location, double r) {
		super(location);
		this.r = r;
		this.i = 0;
	}

	@Override
	public String toString() {
		return String.valueOf(r + "," + i);
	}

	@Override
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}

	@Override
	public boolean isReal() {
		return i == 0;
	}

	public double r() {
		return r;
	}

	public double i() {
		return i;
	}
}