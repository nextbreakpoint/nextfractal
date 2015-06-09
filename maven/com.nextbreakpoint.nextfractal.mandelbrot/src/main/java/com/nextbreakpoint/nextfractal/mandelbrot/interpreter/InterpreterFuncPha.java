package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.funcPha;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterFuncPha implements CompiledExpression {
	private CompiledExpression[] arguments;
	private int index;
	
	public InterpreterFuncPha(ExpressionContext context, CompiledExpression[] arguments) {
		this.index = context.newNumberIndex();
		this.arguments = arguments;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return funcPha(arguments[0].evaluate(context, scope));
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return context.getNumber(index).set(funcPha(arguments[0].evaluate(context, scope)));
	}

	@Override
	public boolean isReal() {
		return true;
	}
}
