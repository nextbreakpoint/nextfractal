package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterVariable extends InterpreterCompiledExpression {
	private String name;

	public InterpreterVariable(String name) {
		this.name = name;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		MutableNumber var = context.getVariable(name);
		return var.r();
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		MutableNumber var = context.getVariable(name);
		return var;
	}
}
