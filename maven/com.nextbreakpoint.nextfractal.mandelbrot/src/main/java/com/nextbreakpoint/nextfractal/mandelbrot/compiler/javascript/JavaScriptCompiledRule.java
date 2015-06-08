package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledRule;

public class JavaScriptCompiledRule implements CompiledRule {
	private String code;

	public JavaScriptCompiledRule(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
