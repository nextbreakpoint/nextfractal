package com.nextbreakpoint.nextfractal.flux.grammar;

public interface ASTExpressionCompiler {
	public void compile(ASTNumber number);

	public void compile(ASTFunction function);

	public void compile(ASTOperator operator);

	public void compile(ASTParen paren);

	public void compile(ASTVariable variable);
}
