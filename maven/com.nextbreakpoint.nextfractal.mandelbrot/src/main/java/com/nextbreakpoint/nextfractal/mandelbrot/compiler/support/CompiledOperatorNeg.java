package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.*;

public class CompiledOperatorNeg implements CompiledExpression {
	private CompiledExpression exp;
	private int index;
	
	public CompiledOperatorNeg(ExpressionContext context, CompiledExpression exp) {
		this.index = context.newNumberIndex();
		this.exp = exp;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return -exp.evaluateReal(context, scope);
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return opNeg(context.getNumber(index), exp.evaluate(context, scope));
	}

	@Override
	public boolean isReal() {
		return exp.isReal();
	}
}
