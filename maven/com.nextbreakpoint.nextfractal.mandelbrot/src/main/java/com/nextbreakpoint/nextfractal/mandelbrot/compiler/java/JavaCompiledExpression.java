package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public class JavaCompiledExpression implements CompiledExpression {
	private String code;

	public JavaCompiledExpression(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
