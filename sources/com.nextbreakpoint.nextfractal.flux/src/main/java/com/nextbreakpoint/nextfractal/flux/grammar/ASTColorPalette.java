package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTColorPalette extends ASTColorExpression {
	private String name;
	private int index;
	
	public ASTColorPalette(Token location, String name, int index) {
		super(location);
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[");
		builder.append(index);
		builder.append("]");
		return builder.toString();
	}
}
