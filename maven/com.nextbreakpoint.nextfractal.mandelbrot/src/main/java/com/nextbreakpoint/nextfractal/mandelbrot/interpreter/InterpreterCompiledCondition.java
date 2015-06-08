package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;

public abstract class InterpreterCompiledCondition implements CompiledCondition {
	public abstract boolean evaluate(InterpreterContext context);
}
