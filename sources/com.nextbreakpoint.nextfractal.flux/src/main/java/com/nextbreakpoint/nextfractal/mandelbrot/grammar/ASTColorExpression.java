package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTColorExpression extends ASTObject {
	public ASTColorExpression(Token location) {
		super(location);
	}

	public abstract void compile(ASTExpressionCompiler compiler);
}
