package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledStatement;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class InterpreterStopStatement implements CompiledStatement {
	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return true;
	}
}
