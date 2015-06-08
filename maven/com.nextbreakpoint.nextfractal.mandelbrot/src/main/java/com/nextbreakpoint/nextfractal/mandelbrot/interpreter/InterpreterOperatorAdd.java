package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.*;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterOperatorAdd implements CompiledExpression {
	private CompiledExpression exp1;
	private CompiledExpression exp2;
	private int index;
	
	public InterpreterOperatorAdd(ExpressionContext context, CompiledExpression exp1, CompiledExpression exp2) {
		this.index = context.newNumberIndex();
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return opAdd(exp1.evaluateReal(context), exp2.evaluateReal(context));
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		return opAdd(context.getNumber(index), exp1.evaluate(context), exp2.evaluateReal(context));
	}

	@Override
	public boolean isReal() {
		return exp1.isReal() && exp2.isReal();
	}
}
