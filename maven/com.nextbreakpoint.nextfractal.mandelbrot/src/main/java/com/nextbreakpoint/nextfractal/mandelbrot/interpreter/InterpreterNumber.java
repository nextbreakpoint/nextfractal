package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterNumber extends InterpreterCompiledExpression {
	private double r;
	private double i;
	private int index;

	public InterpreterNumber(ExpressionContext context, double r, double i) {
		this.index = context.newNumberIndex();
		this.r = r;
		this.i = i;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return r;
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		MutableNumber number = context.getNumber(index);
		number.set(r, i);
		return number;
	}

	@Override
	public boolean isReal() {
		return i == 0;
	}
}
