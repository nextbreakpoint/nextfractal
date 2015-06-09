package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.Map;

public interface CompiledStatement {
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
