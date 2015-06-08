package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.*;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterFuncMod implements CompiledExpression {
	private CompiledExpression[] arguments;
	private int index;
	
	public InterpreterFuncMod(ExpressionContext context, CompiledExpression[] arguments) {
		this.index = context.newNumberIndex();
		this.arguments = arguments;
	}

	@Override
	public double evaluateReal(InterpreterContext context) {
		return funcMod(arguments[0].evaluateReal(context));
	}

	@Override
	public Number evaluate(InterpreterContext context) {
		return context.getNumber(index).set(funcMod(arguments[0].evaluate(context)));
	}

	@Override
	public boolean isReal() {
		return true;
	}
}
