package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class IntrepreterJuliaCondition implements CompiledCondition {
	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return context.isJulia();
	}
}
