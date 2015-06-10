package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class CompiledStopStatement implements CompiledStatement {
	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return true;
	}
}
