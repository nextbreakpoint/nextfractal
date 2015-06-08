package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;

public class JavaCompiledColor implements CompiledColor {
	private String code;

	public JavaCompiledColor(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
}
