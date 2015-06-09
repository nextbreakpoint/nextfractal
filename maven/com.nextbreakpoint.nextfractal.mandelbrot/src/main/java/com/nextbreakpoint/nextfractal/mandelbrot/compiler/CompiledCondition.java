package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.Map;

public interface CompiledCondition {
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
