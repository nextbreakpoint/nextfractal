package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledRule;

public class JavaCompiledRule implements CompiledRule {
	private String code;

	public JavaCompiledRule(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
