package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;

public class JavaCompiledStatement implements CompiledStatement {
	private String code;

	public JavaCompiledStatement(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
