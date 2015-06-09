package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class InterpreterTrapInvertedCondition implements CompiledCondition {
	private String name;
	private CompiledExpression exp;
	
	public InterpreterTrapInvertedCondition(String name, CompiledExpression exp) {
		this.name = name;
		this.exp = exp;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return !context.getTrap(name).contains(exp.evaluate(context, scope));
	}
}
