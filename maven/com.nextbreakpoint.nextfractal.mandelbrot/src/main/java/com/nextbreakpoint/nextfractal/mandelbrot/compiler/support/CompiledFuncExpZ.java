package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.funcExp;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.grammar.ASTException;

public class CompiledFuncExpZ implements CompiledExpression {
	private CompiledExpression[] arguments;
	private int index;
	
	public CompiledFuncExpZ(ExpressionContext context, CompiledExpression[] arguments) {
		this.index = context.newNumberIndex();
		this.arguments = arguments;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		throw new ASTException("Cannot assign function output to real number", null);
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return funcExp(context.getNumber(index), arguments[0].evaluate(context, scope));
	}

	@Override
	public boolean isReal() {
		return false;
	}
}
