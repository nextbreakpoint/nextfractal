package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;

public abstract class InterpreterCompiledColor implements CompiledColor {
	public abstract float[] evaluate(InterpreterContext context);
}
