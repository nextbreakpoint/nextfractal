package com.nextbreakpoint.nextfractal.contextfree.grammar;

import org.antlr.v4.runtime.Token;

class ASTIf extends ASTReplacement {
	private ASTExpression condition;
	private ASTRepContainer thenBody = new ASTRepContainer();
	private ASTRepContainer elseBody = new ASTRepContainer();
	
	public ASTIf(ASTExpression exp, Token location) {
		super(null, ERepElemType.empty, location);
		this.condition = exp;
	}
	
	public ASTRepContainer getThenBody() {
		return thenBody;
	}
	
	public ASTRepContainer getElseBody() {
		return elseBody;
	}

	@Override
	public void compile(ECompilePhase ph) {
		super.compile(ph);
		if (condition != null) {
			condition.compile(ph);
		}
		thenBody.compile(ph, null, null);
		elseBody.compile(ph, null, null);
		
		switch (ph) {
			case TypeCheck:
				if (condition.getType() != EExpType.NumericType || condition.evaluate(null, 0) != 1) {
					error("If condition must be a numeric scalar");
				}
				break;
	
			case Simplify:
				if (condition != null) {
					condition.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		double[] cond = new double[1];
		if (condition.evaluate(cond, 1) != 1) {
			error("Error evaluating if condition");
			return;
		}
		if (cond[0] != 0) {
			thenBody.traverse(parent, tr, rti, false);
		} else {
			elseBody.traverse(parent, tr, rti, false);
		}
	}
}