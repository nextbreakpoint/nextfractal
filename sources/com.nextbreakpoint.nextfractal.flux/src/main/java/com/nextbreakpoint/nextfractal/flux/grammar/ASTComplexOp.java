package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTComplexOp extends ASTComplexExpression {
	private String op;
	private ASTComplexExpression exp1;
	private ASTComplexExpression exp2;

	public ASTComplexOp(Token location, String op, ASTComplexExpression exp) {
		super(location);
		this.op = op;
		this.exp1 = exp;
		this.exp2 = null;
	}

	public ASTComplexOp(Token location, String op, ASTComplexExpression exp1, ASTComplexExpression exp2) {
		super(location);
		this.op = op;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	public String getOp() {
		return op;
	}
	
	public ASTComplexExpression getExp1() {
		return exp1;
	}

	public ASTComplexExpression getExp2() {
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
	public void compile(StringBuilder builder) {
		if (exp2 == null) {
			switch (op) {
				case "-":
					builder.append("opNeg");
					break;
				
				default:
					builder.append("opPos");
					break;
			}
			builder.append("(");
			exp1.compile(builder);
			builder.append(")");
		} else {
			switch (op) {
				case "+":
					builder.append("opAdd");
					break;
				
				case "-":
					builder.append("opSub");
					break;
					
				case "*":
					builder.append("opMul");
					break;
					
				case "^":
					builder.append("opPow");
					break;
				
				default:
					break;
			}
			builder.append("(");
			exp1.compile(builder);
			builder.append(",");
			exp2.compile(builder);
			builder.append(")");
		}
	}
}