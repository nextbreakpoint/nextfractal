package com.nextbreakpoint.nextfractal.flux.grammar;

public class ASTRegion {
	private ASTNumber a; 
	private ASTNumber b; 

	public ASTRegion(ASTNumber a, ASTNumber b) {
		this.a = a;
		this.b = b;
	}

	public ASTNumber getA() {
		return a;
	}

	public ASTNumber getB() {
		return b;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(a);
		builder.append(",");
		builder.append(b);
		builder.append("]");
		return builder.toString();
	}
}