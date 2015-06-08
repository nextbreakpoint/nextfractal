package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;

public class JavaCompiledCondition implements CompiledCondition {
	private String code;

	public JavaCompiledCondition(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
