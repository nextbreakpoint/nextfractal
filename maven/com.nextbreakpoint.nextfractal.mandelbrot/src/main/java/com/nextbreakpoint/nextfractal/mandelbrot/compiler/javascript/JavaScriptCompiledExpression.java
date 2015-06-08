package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public class JavaScriptCompiledExpression implements CompiledExpression {
	private String code;

	public JavaScriptCompiledExpression(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return 0;
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		return null;
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
