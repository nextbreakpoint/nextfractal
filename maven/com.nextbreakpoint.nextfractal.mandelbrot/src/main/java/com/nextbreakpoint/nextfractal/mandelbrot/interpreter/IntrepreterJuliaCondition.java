package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;

public class IntrepreterJuliaCondition implements CompiledCondition {
	@Override
	public boolean evaluate(InterpreterContext context) {
		return context.isJulia();
	}
}
