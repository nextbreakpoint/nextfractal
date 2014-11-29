package com.nextbreakpoint.nextfractal.flux.grammar;

public interface ASTExpressionCompiler {
	public void compile(ASTComplex complex);

	public void compile(ASTComplexFunction function);

	public void compile(ASTComplexOp op);

	public void compile(ASTComplexParen paren);

	public void compile(ASTComplexVariable variable);

	public void compile(ASTReal real);

	public void compile(ASTRealFunction function);

	public void compile(ASTRealOp op);

	public void compile(ASTRealParen paren);

	public void compile(ASTRealVariable variable);
}
