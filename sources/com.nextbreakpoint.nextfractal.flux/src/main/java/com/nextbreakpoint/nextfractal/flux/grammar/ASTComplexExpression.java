package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public abstract class ASTComplexExpression extends ASTObject {
	public ASTComplexExpression(Token location) {
		super(location);
	}

	public abstract void compile(StringBuilder builder);
}