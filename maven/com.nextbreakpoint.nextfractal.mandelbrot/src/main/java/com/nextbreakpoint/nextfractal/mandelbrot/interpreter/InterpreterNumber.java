package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterNumber extends InterpreterCompiledExpression {
	private double r;
	private double i;
	private int index;

	public InterpreterNumber(int index, double r, double i) {
		this.index = index;
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
}
