package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTFunction extends ASTExpression {
	private String name;
	private ASTExpression[] arguments;

	public ASTFunction(Token location, String name, ASTExpression[] arguments) {
		super(location);
		this.name = name;
		this.arguments = arguments;
	}

	public String getName() {
		return name;
	}

	public ASTExpression[] getArguments() {
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
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}

	@Override
	public boolean isReal() {
		if (name.equals("mod") || name.equals("pha")) {
			return true;
		}
		for (ASTExpression argument : arguments) {
			if (!argument.isReal()) {
				return false;
			}
		}
		return true;
	}
}