package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
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
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return 0;
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return null;
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
