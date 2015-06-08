package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;

public class InterpreterLogicOperatorGreather implements CompiledCondition {
	private CompiledExpression[] operands;
	
	public InterpreterLogicOperatorGreather(ExpressionContext context, CompiledExpression[] operands) {
		this.operands = operands;
	}

	@Override
	public boolean evaluate(InterpreterContext context) {
		return operands[0].evaluateReal(context) > operands[1].evaluateReal(context);
	}
}
