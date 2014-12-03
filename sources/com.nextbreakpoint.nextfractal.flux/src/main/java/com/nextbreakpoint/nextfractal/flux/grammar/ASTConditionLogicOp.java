package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTConditionLogicOp extends ASTConditionExpression {
	private String op;
	private ASTConditionExpression exp1;
	private ASTConditionExpression exp2;

	public ASTConditionLogicOp(Token location, String op, ASTConditionExpression exp1, ASTConditionExpression exp2) {
		super(location);
		this.op = op;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	public String getOp() {
		return op;
	}
	
	public ASTConditionExpression getExp1() {
		return exp1;
	}

	public ASTConditionExpression getExp2() {
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

	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}