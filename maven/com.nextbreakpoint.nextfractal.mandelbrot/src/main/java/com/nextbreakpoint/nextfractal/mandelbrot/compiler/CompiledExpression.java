package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public interface CompiledExpression {
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope);
	
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);

	public boolean isReal();
}
