package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;

public class InterpreterTrapCondition implements CompiledCondition {
	private String name;
	private CompiledExpression exp;
	
	public InterpreterTrapCondition(String name, CompiledExpression exp) {
		this.name = name;
		this.exp = exp;
	}

	@Override
	public boolean evaluate(InterpreterContext context) {
		return context.getTrap(name).contains(exp.evaluate(context));
	}
}
