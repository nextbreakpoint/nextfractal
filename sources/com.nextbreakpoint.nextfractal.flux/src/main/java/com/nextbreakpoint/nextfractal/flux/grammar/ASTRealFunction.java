package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRealFunction extends ASTRealExpression {
	private String name;
	private ASTComplexExpression[] arguments;

	public ASTRealFunction(Token location, String name, ASTRealExpression[] arguments) {
		super(location);
		this.name = name;
		this.arguments = arguments;
	}

	public ASTRealFunction(Token location, String name, ASTComplexExpression[] arguments) {
		super(location);
		this.name = name;
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public ASTComplexExpression[] getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("(");
		for (int i = 0; i < arguments.length; i++) {
			builder.append(arguments[i]);
			if (i < arguments.length - 1) {
				builder.append(",");
			}
		}
		builder.append(")");
		return builder.toString();
	}
	
	@Override
	public void compile(StringBuilder builder) {
		builder.append("func");
		builder.append(name.toUpperCase().substring(0, 1));
		builder.append(name.substring(1));
		builder.append("(");
		for (int i = 0; i < arguments.length; i++) {
			arguments[i].compile(builder);
			if (i < arguments.length - 1) {
				builder.append(",");
			}
		}
		builder.append(")");
	}
}