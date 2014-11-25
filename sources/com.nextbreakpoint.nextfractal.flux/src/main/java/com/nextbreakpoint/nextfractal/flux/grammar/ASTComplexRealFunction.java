package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTComplexRealFunction extends ASTRealFunction {
	public ASTComplexRealFunction(Token location, String name, ASTComplexExpression argument) {
		super(location, name, new ASTComplexExpression[] { argument });
	}
}