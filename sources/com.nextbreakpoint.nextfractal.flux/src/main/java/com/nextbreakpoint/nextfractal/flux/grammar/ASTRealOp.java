package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTRealOp extends ASTRealExpression {
	private String op;
	private ASTRealExpression exp1;
	private ASTRealExpression exp2;

	public ASTRealOp(Token location, String op, ASTRealExpression exp) {
		super(location);
		this.op = op;
		this.exp1 = exp;
		this.exp2 = null;
	}

	public ASTRealOp(Token location, String op, ASTRealExpression exp1, ASTRealExpression exp2) {
		super(location);
		this.op = op;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	public String getOp() {
		return op;
	}
	
	public ASTRealExpression getExp1() {
		return exp1;
	}

	public ASTRealExpression getExp2() {
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
					
				case "/":
					builder.append("opDiv");
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