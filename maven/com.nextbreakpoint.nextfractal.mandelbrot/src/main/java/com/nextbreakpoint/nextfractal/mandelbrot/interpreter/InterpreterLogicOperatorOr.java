package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;

public class InterpreterLogicOperatorOr implements CompiledCondition {
	private CompiledCondition[] operands;
	
	public InterpreterLogicOperatorOr(ExpressionContext context, CompiledCondition[] operands) {
		this.operands = operands;
	}

	@Override
	public boolean evaluate(InterpreterContext context) {
		return operands[0].evaluate(context) || operands[1].evaluate(context);
	}
}
