package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public interface CompiledColor {
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
