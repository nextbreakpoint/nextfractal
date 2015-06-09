package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColorExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class InterpreterPalette implements CompiledColorExpression {
	private String name;
	private CompiledExpression exp;
	
	public InterpreterPalette(String name, CompiledExpression exp) {
		this.name = name;
		this.exp = exp;
	}

	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return context.getPalette(name).get(exp.evaluateReal(context, scope));
	}
}
