package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public abstract class InterpreterCompiledExpression implements CompiledExpression {
	public abstract double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope);
	
	public abstract Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope);
}
