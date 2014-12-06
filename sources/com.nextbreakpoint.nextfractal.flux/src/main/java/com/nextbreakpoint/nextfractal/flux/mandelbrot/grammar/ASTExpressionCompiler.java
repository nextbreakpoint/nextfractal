package com.nextbreakpoint.nextfractal.flux.mandelbrot.grammar;

public interface ASTExpressionCompiler {
	public void compile(ASTNumber number);

	public void compile(ASTFunction function);

	public void compile(ASTOperator operator);

	public void compile(ASTParen paren);

	public void compile(ASTVariable variable);

	public void compile(ASTConditionCompareOp compareOp);

	public void compile(ASTConditionLogicOp logicOp);

	public void compile(ASTConditionTrap trap);

	public void compile(ASTRuleLogicOpExpression astRuleLogicOpExpression);

	public void compile(ASTRuleCompareOpExpression astRuleOpExpression);

	public void compile(ASTColorPalette astColorPalette);

	public void compile(ASTColorComponent astColorComponent);
}
