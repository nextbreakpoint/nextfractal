package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTRealExpression extends ASTComplexExpression {
	public ASTRealExpression(Token location) {
		super(location);
	}
}