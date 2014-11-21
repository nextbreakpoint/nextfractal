package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

class ASTReal extends ASTExpression {
	private double value;
	private String text;

	public ASTReal(Token location, double value) {
		super(location);
		this.value = value;
	}

	public ASTReal(Token location, String text, boolean negative) {
		super(location);
		if (negative) {
			this.text = "-" + text;
			this.value = Double.parseDouble(text);
		} else {
			this.text = text;
			this.value = Double.parseDouble(text);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public double getValue() {
		return value;
	}
}