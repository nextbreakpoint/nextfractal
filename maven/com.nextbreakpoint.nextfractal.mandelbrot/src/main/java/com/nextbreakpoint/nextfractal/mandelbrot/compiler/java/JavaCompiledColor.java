package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public class JavaCompiledColor implements CompiledColor {
	private String code;

	public JavaCompiledColor(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return null;
	}
}
