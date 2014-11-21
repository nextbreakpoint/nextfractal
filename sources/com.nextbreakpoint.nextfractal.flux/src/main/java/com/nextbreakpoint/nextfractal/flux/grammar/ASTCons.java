package com.nextbreakpoint.nextfractal.flux.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTCons extends ASTExpression {
	private List<ASTExpression> children = new ArrayList<ASTExpression>();

	public ASTCons(Token location, ASTExpression... kids) {
		super(location);
	}
}