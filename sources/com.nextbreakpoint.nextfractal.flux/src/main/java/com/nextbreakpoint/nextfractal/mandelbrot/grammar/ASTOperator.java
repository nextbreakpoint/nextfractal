package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

public class ASTOperator extends ASTExpression {
	private String op;
	private ASTExpression exp1;
	private ASTExpression exp2;

	public ASTOperator(Token location, String op, ASTExpression exp) {
		super(location);
		this.op = op;
		this.exp1 = exp;
		this.exp2 = null;
	}

	public ASTOperator(Token location, String op, ASTExpression exp1, ASTExpression exp2) {
		super(location);
		this.op = op;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	public String getOp() {
		return op;
	}
	
	public ASTExpression getExp1() {
		return exp1;
	}

	public ASTExpression getExp2() {
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

	public boolean isReal() {
		return exp1.isReal() && (exp2 !=null && exp2.isReal());
	}
}