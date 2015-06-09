package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.Map;

public interface CompiledColorExpression {
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
