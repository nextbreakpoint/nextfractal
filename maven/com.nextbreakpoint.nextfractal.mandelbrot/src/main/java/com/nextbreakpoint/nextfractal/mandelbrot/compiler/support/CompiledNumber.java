package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledNumber implements CompiledExpression {
	private double r;
	private double i;
	private int index;

	public CompiledNumber(ExpressionContext context, double r, double i) {
		this.index = context.newNumberIndex();
		this.r = r;
		this.i = i;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return r;
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		MutableNumber number = context.getNumber(index);
		number.set(r, i);
		return number;
	}

	@Override
	public boolean isReal() {
		return i == 0;
	}
}
