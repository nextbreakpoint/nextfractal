package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;

public class InterpreterPalette implements CompiledColor {
	private String name;
	private CompiledExpression exp;
	
	public InterpreterPalette(String name, CompiledExpression exp) {
		this.name = name;
		this.exp = exp;
	}

	@Override
	public float[] evaluate(InterpreterContext context) {
		return context.getPalette(name).get(exp.evaluateReal(context));
	}
}
