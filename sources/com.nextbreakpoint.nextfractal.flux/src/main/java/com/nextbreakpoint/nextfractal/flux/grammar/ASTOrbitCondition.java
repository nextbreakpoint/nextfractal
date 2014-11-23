package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTOrbitCondition extends ASTObject {
	private ASTConditionExpression exp;
	
	public ASTOrbitCondition(Token location, ASTConditionExpression exp) {
		super(location);
		this.exp = exp;
	}

	public ASTConditionExpression getExpression() {
		return exp;
	}
}