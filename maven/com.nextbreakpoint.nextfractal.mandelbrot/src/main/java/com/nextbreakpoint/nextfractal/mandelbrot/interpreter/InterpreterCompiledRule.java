package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledRule;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Rule;

public abstract class InterpreterCompiledRule implements CompiledRule {
	public abstract Rule evaluate(InterpreterContext context);
}
