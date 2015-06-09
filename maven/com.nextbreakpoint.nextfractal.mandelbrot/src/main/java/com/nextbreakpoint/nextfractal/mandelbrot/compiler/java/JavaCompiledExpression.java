package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

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
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
