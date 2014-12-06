package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRealFunction extends ASTFunction {
	public ASTRealFunction(Token location, String name, ASTExpression argument) {
		super(location, name, new ASTExpression[] { argument });
	}
}