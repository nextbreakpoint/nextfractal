package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.*;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterFuncPow implements CompiledExpression {
	private CompiledExpression[] arguments;
	private int index;
	
	public InterpreterFuncPow(ExpressionContext context, CompiledExpression[] arguments) {
		this.index = context.newNumberIndex();
		this.arguments = arguments;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return funcPow(arguments[0].evaluateReal(context), arguments[1].evaluateReal(context));
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		return funcPow(context.getNumber(index), arguments[0].evaluate(context), arguments[1].evaluateReal(context));
	}

	@Override
	public boolean isReal() {
		return arguments[0].isReal();
	}
}
