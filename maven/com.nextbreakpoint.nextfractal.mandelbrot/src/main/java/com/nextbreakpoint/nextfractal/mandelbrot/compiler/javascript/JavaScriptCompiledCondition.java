package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;

public class JavaScriptCompiledCondition implements CompiledCondition {
	private String code;

	public JavaScriptCompiledCondition(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
