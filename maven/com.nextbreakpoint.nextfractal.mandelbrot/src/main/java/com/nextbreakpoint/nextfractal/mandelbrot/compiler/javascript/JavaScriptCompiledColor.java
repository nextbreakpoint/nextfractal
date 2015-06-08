package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;

public class JavaScriptCompiledColor implements CompiledColor {
	private String code;

	public JavaScriptCompiledColor(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
