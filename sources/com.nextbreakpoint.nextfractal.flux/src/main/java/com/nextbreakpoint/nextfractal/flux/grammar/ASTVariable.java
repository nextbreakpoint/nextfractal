package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.flux.Variable;

public class ASTVariable extends ASTExpression {
	private Variable variable;

	public ASTVariable(Token location, Variable variable) {
		super(location);
		this.variable = variable;
	}

	public String getName() {
		return variable.getName();
	}

	public boolean isReal() {
		return variable.isReal();
	}

	@Override
	public String toString() {
		return variable.getName();
	}

	@Override
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}