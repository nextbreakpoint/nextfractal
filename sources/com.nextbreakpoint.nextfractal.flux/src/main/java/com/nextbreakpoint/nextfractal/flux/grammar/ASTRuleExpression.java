package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTRuleExpression extends ASTObject {
	public ASTRuleExpression(Token location) {
		super(location);
	}

	public abstract void compile(ASTExpressionCompiler compiler);
}
