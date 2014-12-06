package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTConditionExpression extends ASTObject {
	public ASTConditionExpression(Token location) {
		super(location);
	}

	public abstract void compile(ASTExpressionCompiler compiler);
}