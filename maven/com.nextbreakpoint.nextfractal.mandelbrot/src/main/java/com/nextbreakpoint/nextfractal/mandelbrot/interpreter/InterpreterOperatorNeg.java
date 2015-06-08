package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.*;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterOperatorNeg implements CompiledExpression {
	private CompiledExpression exp;
	private int index;
	
	public InterpreterOperatorNeg(ExpressionContext context, CompiledExpression exp) {
		this.index = context.newNumberIndex();
		this.exp = exp;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return -exp.evaluateReal(context);
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		return opNeg(context.getNumber(index), exp.evaluate(context));
	}

	@Override
	public boolean isReal() {
		return exp.isReal();
	}
}
