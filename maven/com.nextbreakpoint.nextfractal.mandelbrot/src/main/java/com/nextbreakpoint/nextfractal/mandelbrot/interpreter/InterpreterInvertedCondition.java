package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class InterpreterInvertedCondition implements CompiledCondition {
	private CompiledCondition condition;
	
	public InterpreterInvertedCondition(CompiledCondition condition) {
		this.condition = condition;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return !condition.evaluate(context, scope);
	}
}
