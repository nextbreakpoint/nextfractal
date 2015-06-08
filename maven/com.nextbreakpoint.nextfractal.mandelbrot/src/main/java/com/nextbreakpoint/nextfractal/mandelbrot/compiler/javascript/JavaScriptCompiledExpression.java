package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;

public class JavaScriptCompiledExpression implements CompiledExpression {
	private String code;

	public JavaScriptCompiledExpression(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
