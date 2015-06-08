package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public abstract class InterpreterCompiledExpression implements CompiledExpression {
	public abstract double evaluateReal(InterpreterContext context);
	
	public abstract Number evaluate(InterpreterContext context);
}
