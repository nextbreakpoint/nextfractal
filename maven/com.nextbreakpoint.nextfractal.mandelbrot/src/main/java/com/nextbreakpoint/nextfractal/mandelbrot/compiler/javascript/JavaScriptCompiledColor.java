package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public class JavaScriptCompiledColor implements CompiledColor {
	private String code;

	public JavaScriptCompiledColor(String code) {
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
