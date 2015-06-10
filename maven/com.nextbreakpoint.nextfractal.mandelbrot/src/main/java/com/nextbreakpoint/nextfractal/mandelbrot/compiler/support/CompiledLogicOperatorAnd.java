package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;

public class CompiledLogicOperatorAnd implements CompiledCondition {
	private CompiledCondition[] operands;
	
	public CompiledLogicOperatorAnd(ExpressionContext context, CompiledCondition[] operands) {
		this.operands = operands;
	}

	@Override
	public boolean evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return operands[0].evaluate(context, scope) && operands[1].evaluate(context, scope);
	}
}
