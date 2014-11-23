package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTComplexMod extends ASTRealExpression {
	private ASTComplexExpression exp;

	public ASTComplexMod(Token location, ASTComplexExpression exp) {
		super(location);
		this.exp = exp;
	}
	
	public ASTComplexExpression getExp() {
		return exp;
	}
}