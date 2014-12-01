package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTOrbitProjection extends ASTObject {
	private ASTExpression exp;
	
	public ASTOrbitProjection(Token location, ASTExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTExpression getExpression() {
		return exp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(exp);
		return builder.toString();
	}
}