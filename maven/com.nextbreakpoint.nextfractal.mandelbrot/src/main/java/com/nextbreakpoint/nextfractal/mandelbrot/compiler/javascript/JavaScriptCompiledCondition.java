package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public class JavaScriptCompiledCondition implements CompiledCondition {
	private String code;

	public JavaScriptCompiledCondition(String code) {
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
