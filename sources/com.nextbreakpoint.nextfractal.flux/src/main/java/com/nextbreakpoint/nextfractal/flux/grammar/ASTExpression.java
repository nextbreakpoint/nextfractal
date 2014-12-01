package com.nextbreakpoint.nextfractal.flux.grammar;

public interface ASTExpression extends ASTObject {
	public void compile(ASTExpressionCompiler compiler);
}