package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTRuleExpression extends ASTObject {
	public ASTRuleExpression(Token location) {
		super(location);
	}

	public abstract void compile(ASTExpressionCompiler compiler);
}
