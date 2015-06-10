package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public interface CompiledColorExpression {
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
