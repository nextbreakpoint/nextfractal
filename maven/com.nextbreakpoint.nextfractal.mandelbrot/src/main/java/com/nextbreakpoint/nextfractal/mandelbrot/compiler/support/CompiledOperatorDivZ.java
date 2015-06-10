package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.opDiv;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;

public class CompiledOperatorDivZ implements CompiledExpression {
	private CompiledExpression exp1;
	private CompiledExpression exp2;
	private int index;
	
	public CompiledOperatorDivZ(ExpressionContext context, CompiledExpression exp1, CompiledExpression exp2) {
		this.index = context.newNumberIndex();
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		throw new ASTException("Cannot assign operator result to real number", null);
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return opDiv(context.getNumber(index), exp1.evaluate(context, scope), exp2.evaluate(context, scope));
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
