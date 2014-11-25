package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTOrbitCondition extends ASTObject {
	private ASTConditionExpression exp;
	
	public ASTOrbitCondition(Token location, ASTConditionExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTConditionExpression getExpression() {
		return exp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(exp);
		return builder.toString();
	}
}