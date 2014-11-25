package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTPalette extends ASTObject {
	private String name;

	public ASTPalette(Token location, String name) {
		super(location);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[");
		builder.append("]");
		return builder.toString();
	}
}