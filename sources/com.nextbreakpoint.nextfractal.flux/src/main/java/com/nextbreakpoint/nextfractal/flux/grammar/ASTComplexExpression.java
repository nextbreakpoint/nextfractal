package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTComplexExpression extends ASTObjectImpl implements ASTExpression {
	public ASTComplexExpression(Token location) {
		super(location);
	}

	public boolean isReal() {
		return false;
	}
}