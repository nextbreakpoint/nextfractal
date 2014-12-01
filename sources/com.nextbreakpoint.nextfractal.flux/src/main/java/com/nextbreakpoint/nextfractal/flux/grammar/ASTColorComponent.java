package com.nextbreakpoint.nextfractal.flux.grammar;

import org.antlr.v4.runtime.Token;

public class ASTColorComponent extends ASTColorExpression {
	private ASTExpression exp1;
	private ASTExpression exp2;
	private ASTExpression exp3;
	private ASTExpression exp4;
	
	public ASTColorComponent(Token location, ASTExpression exp1, ASTExpression exp2, ASTExpression exp3, ASTExpression exp4) {
		super(location);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
	}

	public ASTColorComponent(Token location, ASTExpression exp1, ASTExpression exp2, ASTExpression exp3) {
		super(location);
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = null;
	}

	public ASTColorComponent(Token location, ASTExpression exp1) {
		super(location);
		this.exp1 = exp1;
		this.exp2 = null;
		this.exp3 = null;
		this.exp4 = null;
	}

	public ASTExpression getExp1() {
		return exp1;
	}

	public ASTExpression getExp2() {
		return exp2;
	}

	public ASTExpression getExp3() {
		return exp3;
	}

	public ASTExpression getExp4() {
		return exp4;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(exp1);
		if (exp2 != null) {
			builder.append(',');
			builder.append(exp2);
		}
		if (exp3 != null) {
			builder.append(',');
			builder.append(exp3);
		}
		if (exp4 != null) {
			builder.append(',');
			builder.append(exp4);
		}
		return builder.toString();
	}
}
