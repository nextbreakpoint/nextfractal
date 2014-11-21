package com.nextbreakpoint.nextfractal.flux.orbit.equation;

import org.antlr.v4.runtime.Token;

class ASTOperator extends ASTExpression {
	private char op;
	private ASTExpression left;
	private ASTExpression right;

	public ASTOperator(Token location, char op, ASTExpression left, ASTExpression right) {
		super(location);
		this.op = op;
		this.left = left;
		this.right = right;
		int index = "NP!+-*/^_<>LG=n&|X".indexOf(""+op);
		if (index == -1) {
			throw new RuntimeException("Unknown operator");
		} else if (index < 3) {
			if (right != null) {
				throw new RuntimeException("Operator takes only one operand");
			}
		} else {
			if (right != null) {
				throw new RuntimeException("Operator takes two operands");
			}
		}
	}

	public ASTOperator(Token location, char op, ASTExpression left) {
		this(location, op, left, null);
	}

	public char getOp() {
		return op;
	}

	public ASTExpression getLeft() {
		return left;
	}

	public ASTExpression getRight() {
		return right;
	}
}