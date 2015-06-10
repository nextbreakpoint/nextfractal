package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.funcPow;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;

public class CompiledFuncPowZ implements CompiledExpression {
	private CompiledExpression[] arguments;
	private int index;
	
	public CompiledFuncPowZ(ExpressionContext context, CompiledExpression[] arguments) {
		this.index = context.newNumberIndex();
		this.arguments = arguments;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		throw new ASTException("Cannot assign function output to real number", null);
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return funcPow(context.getNumber(index), arguments[0].evaluate(context, scope), arguments[1].evaluateReal(context, scope));
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
