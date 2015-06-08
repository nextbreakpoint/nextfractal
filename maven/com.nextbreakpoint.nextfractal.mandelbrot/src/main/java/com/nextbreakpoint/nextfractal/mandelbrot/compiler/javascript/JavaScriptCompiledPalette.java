package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledPalette;

public class JavaScriptCompiledPalette implements CompiledPalette {
	private String code;

	public JavaScriptCompiledPalette(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
