package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.opPos;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class CompiledOperatorPos implements CompiledExpression {
	private CompiledExpression exp;
	private int index;
	
	public CompiledOperatorPos(ExpressionContext context, CompiledExpression exp) {
		this.index = context.newNumberIndex();
		this.exp = exp;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return exp.evaluateReal(context, scope);
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return opPos(context.getNumber(index), exp.evaluate(context, scope));
	}

	@Override
	public boolean isReal() {
		return exp.isReal();
	}
}
