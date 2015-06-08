package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public interface CompiledExpression {
	public double evaluateReal(InterpreterContext context);
	
	public Number evaluate(InterpreterContext context);

	public boolean isReal();
}
