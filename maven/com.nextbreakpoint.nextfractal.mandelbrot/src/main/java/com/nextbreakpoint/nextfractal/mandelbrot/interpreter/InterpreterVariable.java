package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterVariable extends InterpreterCompiledExpression {
	private String name;
	private boolean real;

	public InterpreterVariable(String name, boolean real) {
		this.name = name;
		this.real = real;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		CompilerVariable var = scope.get(name);
		return var.getValue().r();
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		CompilerVariable var = scope.get(name);
		return var.getValue();
	}

	@Override
	public boolean isReal() {
		return real;
	}
}
