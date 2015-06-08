package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;

public abstract class InterpreterCompiledPalette implements CompiledPalette {
	public abstract Palette evaluate(InterpreterContext context);
}
