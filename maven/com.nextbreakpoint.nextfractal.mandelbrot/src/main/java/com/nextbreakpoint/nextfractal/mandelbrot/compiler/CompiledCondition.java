package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public interface CompiledCondition {
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
