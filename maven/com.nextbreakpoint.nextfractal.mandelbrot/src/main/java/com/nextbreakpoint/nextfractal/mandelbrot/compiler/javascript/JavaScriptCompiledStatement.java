package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;

public class JavaScriptCompiledStatement implements CompiledStatement {
	private String code;

	public JavaScriptCompiledStatement(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
