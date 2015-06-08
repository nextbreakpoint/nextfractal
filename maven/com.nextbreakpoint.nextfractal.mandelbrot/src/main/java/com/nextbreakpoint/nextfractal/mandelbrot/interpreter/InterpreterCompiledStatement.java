package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;

public abstract class InterpreterCompiledStatement implements CompiledStatement {
	public abstract void evaluate(InterpreterContext context);
}
