package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class InterpreterPalette implements CompiledColor {
	private String name;
	private CompiledExpression exp;
	
	public InterpreterPalette(String name, CompiledExpression exp) {
		this.name = name;
		this.exp = exp;
	}

	@Override
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return context.getPalette(name).get(exp.evaluateReal(context, scope));
	}
}
