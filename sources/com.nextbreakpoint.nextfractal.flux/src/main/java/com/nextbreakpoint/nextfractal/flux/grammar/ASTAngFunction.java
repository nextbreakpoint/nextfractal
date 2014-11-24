package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTAngFunction extends ASTRealFunction {
	public ASTAngFunction(Token location, ASTComplexExpression argument) {
		super(location, "ang", new ASTComplexExpression[] { argument });
	}
}