package com.nextbreakpoint.nextfractal.flux.orbit.equation;

import org.antlr.v4.runtime.Token;

class ASTVariable extends ASTExpression {
	private String text;
	private int stringIndex;
	private int stackIndex;
	private int count;
	private boolean isParameter;

	public ASTVariable(Token location, int stringIndex, String text) {
		super(location);
		this.stringIndex = stringIndex;
		this.isParameter = false;
		this.stackIndex = 0;
		this.text = text;
		this.count = 0;
	}

	public String getText() {
		return text;
	}

	public int getStringIndex() {
		return stringIndex;
	}

	public int getStackIndex() {
		return stackIndex;
	}

	public int getCount() {
		return count;
	}

	public boolean isParameter() {
		return isParameter;
	}
}