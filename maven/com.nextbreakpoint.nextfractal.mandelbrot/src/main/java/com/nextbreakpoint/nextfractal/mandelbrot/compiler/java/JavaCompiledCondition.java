package com.nextbreakpoint.nextfractal.mandelbrot.compiler.java;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class JavaCompiledCondition implements CompiledCondition {
	private String code;

	public JavaCompiledCondition(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return false;
	}
}
