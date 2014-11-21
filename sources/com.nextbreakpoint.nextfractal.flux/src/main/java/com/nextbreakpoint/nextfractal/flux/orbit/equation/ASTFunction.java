package com.nextbreakpoint.nextfractal.flux.orbit.equation;

import org.antlr.v4.runtime.Token;

class ASTFunction extends ASTExpression {
	private ASTExpression arguments;

	public ASTFunction(Token location, String name, ASTExpression arguments) {
		super(location);
	}
}