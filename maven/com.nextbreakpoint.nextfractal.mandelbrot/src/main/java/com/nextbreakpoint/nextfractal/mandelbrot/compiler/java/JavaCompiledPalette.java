package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledPalette;

public class JavaCompiledPalette implements CompiledPalette {
	private String code;

	public JavaCompiledPalette(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
