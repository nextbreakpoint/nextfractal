package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRuleLogicOpExpression extends ASTRuleExpression {
	private String op;
	private ASTRuleExpression exp1;
	private ASTRuleExpression exp2;
	
	public ASTRuleLogicOpExpression(Token location, String op, ASTRuleExpression exp1, ASTRuleExpression exp2) {
		super(location);
		this.op = op;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public String getOp() {
		return op;
	}
	
	public ASTRuleExpression getExp1() {
		return exp1;
	}

	public ASTRuleExpression getExp2() {
		return exp2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(exp1);
		builder.append(op);
		if (exp2 != null) {
			builder.append(exp2);
		}
		return builder.toString();
	}

	@Override
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}