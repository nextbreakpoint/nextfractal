package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;
import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColorExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class JavaCompiledColorExpression implements CompiledColorExpression {
	private String code;

	public JavaCompiledColorExpression(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return null;
	}
}
