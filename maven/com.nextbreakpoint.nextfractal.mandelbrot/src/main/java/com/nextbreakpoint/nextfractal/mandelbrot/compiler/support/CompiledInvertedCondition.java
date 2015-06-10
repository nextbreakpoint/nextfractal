package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class CompiledInvertedCondition implements CompiledCondition {
	private CompiledCondition condition;
	
	public CompiledInvertedCondition(CompiledCondition condition) {
		this.condition = condition;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return !condition.evaluate(context, scope);
	}
}
