package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterVariable extends InterpreterCompiledExpression {
	private String name;
	private boolean real;

	public InterpreterVariable(String name, boolean real) {
		this.name = name;
		this.real = real;
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

	@Override
	public boolean isReal() {
		return real;
	}
}
