package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTInteger extends ASTRealExpression {
	private int value;

	public ASTInteger(Token location, int value) {
		super(location);
		this.value = value;
	}

	public ASTInteger(Token location, String text) {
		super(location);
		this.value = Integer.parseInt(text);
	}

	public int getValue() {
		return value;
	}
}