package com.nextbreakpoint.nextfractal.contextfree.parser;

import org.antlr.v4.runtime.Token;

class ASTParen extends ASTExpression {
	private ASTExpression expression;
	
	public ASTParen(ASTExpression expression, Token location) {
		super(expression.isConstant(), expression.isNatural(), expression.getType(), location);
		this.expression = expression;
	}

	@Override
	public void entropy(StringBuilder e) {
        expression.entropy(e);
        e.append("\u00E8\u00E9\u00F6\u007E\u001A\u00F1");
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) {
        if (type != EExpType.NumericType) {
            throw new RuntimeException("Non-numeric/flag expression in a numeric/flag context");
        }
        return expression.evaluate(result, length, rti);
	}

	@Override
	public void evaluate(Modification[] result, boolean shapeDest, RTI rti) {
        if (type != EExpType.ModType) {
            throw new RuntimeException("Expression does not evaluate to an adjustment");
        }
		super.evaluate(result, shapeDest, rti);
	}
	
	@Override
	public StackRule evalArgs(RTI rti, StackRule parent) {
		if (type != EExpType.RuleType) {
			throw new RuntimeException("Evaluation of a non-shape expression in a shape context");
		}
		return expression.evalArgs(rti, parent);
	}
	
	@Override
	public ASTExpression simplify() {
		ASTExpression e = expression.simplify();
		return e;
	}

	@Override
	public ASTExpression compile(ECompilePhase ph) {
		if (expression == null) return null;
		
		expression.compile(ph);
		
		switch (ph) {
			case TypeCheck:
				{
					isConstant = expression.isConstant();
					isNatural = expression.isNatural();
					locality = expression.getLocality();
					type = expression.getType();
				}
				break;
	
			case Simplify: 
				break;

			default:
				break;
		}
		return null;
	}
}