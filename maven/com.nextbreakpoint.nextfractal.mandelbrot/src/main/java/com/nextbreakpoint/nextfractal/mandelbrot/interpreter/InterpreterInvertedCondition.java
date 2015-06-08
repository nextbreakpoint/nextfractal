package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;

public class InterpreterInvertedCondition implements CompiledCondition {
	private CompiledCondition condition;
	
	public InterpreterInvertedCondition(CompiledCondition condition) {
		this.condition = condition;
	}

	@Override
	public boolean evaluate(InterpreterContext context) {
		return !condition.evaluate(context);
	}
}
