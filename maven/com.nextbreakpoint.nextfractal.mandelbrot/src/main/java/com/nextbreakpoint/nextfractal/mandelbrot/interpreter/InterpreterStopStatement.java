package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class InterpreterStopStatement implements CompiledStatement {
	public InterpreterStopStatement() {
	}

	@Override
	public void evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		context.stop();
	}
}
