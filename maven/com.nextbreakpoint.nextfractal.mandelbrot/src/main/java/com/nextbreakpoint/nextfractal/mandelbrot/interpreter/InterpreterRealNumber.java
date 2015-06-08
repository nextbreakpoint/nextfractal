package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterRealNumber extends InterpreterCompiledExpression {
	private Number number;

	public InterpreterRealNumber(double value) {
		this.number = new Number(value);
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return number.r();
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		return number;
	}
}
