package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTModFunction extends ASTRealFunction {
	public ASTModFunction(Token location, ASTComplexExpression argument) {
		super(location, "mod", new ASTComplexExpression[] { argument });
	}
}