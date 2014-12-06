package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTExpression extends ASTObject {
	public ASTExpression(Token location) {
		super(location);
	}

	public boolean isReal() {
		return false;
	}

	public abstract void compile(ASTExpressionCompiler compiler);
}