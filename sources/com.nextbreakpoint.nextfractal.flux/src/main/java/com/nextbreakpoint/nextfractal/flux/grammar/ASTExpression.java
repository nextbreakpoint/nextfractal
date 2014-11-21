package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTExpression {
	protected Token location;

	public ASTExpression(Token location) {
		this.location = location;
	}

	public Token getLocation() {
		return location;
	}

	public void setLocation(Token location) {
		this.location = location;
	}

	public ASTExpression append(ASTExpression exp) {
		// TODO Auto-generated method stub
		return null;
	}
}