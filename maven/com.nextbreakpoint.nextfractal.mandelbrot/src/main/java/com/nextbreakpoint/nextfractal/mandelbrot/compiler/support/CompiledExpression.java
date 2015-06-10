package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public interface CompiledExpression {
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope);
	
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);

	public boolean isReal();
}
