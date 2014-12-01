package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTOrbitTrapOp extends ASTObject {
	private String op;
	private ASTNumber c1;
	private ASTNumber c2;

	public ASTOrbitTrapOp(Token location, String op, ASTNumber c) {
		super(location);
		this.op = op;
		this.c1 = c;
		this.c2 = null;
	}

	public ASTOrbitTrapOp(Token location, String op, ASTNumber c1, ASTNumber c2) {
		super(location);
		this.op = op;
		this.c1 = c1;
		this.c2 = c2;
	}

	public String getOp() {
		return op;
	}
	
	public ASTNumber getC1() {
		return c1;
	}

	public ASTNumber getC2() {
		return c2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(op);
		builder.append("(");
		builder.append(c1);
		if (c2 != null) {
			builder.append(",");
			builder.append(c2);
		}
		builder.append(")");
		return builder.toString();
	}
}