package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterVariableZ extends InterpreterCompiledExpression {
	private String name;
	private boolean real;
	private int index;

	public InterpreterVariableZ(ExpressionContext context, String name, boolean real) {
		this.index = context.newNumberIndex();
		this.name = name;
		this.real = real;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return 0;
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		CompilerVariable var = scope.get(name);
		MutableNumber number = context.getNumber(index);
		number.set(var.getValue());
		return number;
	}

	@Override
	public boolean isReal() {
		return real;
	}
}
