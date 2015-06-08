package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Statement;

public abstract class InterpreterCompiledStatement implements CompiledStatement {
	public abstract Statement evaluate(InterpreterContext context);
}
