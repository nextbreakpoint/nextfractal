package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTOrbitProjection extends ASTObject {
	private ASTComplexExpression exp;
	
	public ASTOrbitProjection(Token location, ASTComplexExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTComplexExpression getExpression() {
		return exp;
	}
}