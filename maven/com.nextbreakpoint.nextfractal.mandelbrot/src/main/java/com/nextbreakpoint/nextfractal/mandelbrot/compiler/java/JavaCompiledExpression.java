package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;

public class JavaCompiledExpression implements CompiledExpression {
	private String code;

	public JavaCompiledExpression(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
