package com.nextbreakpoint.nextfractal.mandelbrot.compiler.javascript;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.interpreter.InterpreterContext;

public class JavaScriptCompiledStatement implements CompiledStatement {
	private String code;

	public JavaScriptCompiledStatement(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public void evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
	}
}
