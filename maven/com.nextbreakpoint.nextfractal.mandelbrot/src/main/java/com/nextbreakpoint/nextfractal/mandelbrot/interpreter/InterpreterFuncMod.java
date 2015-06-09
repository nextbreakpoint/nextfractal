package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import static com.nextbreakpoint.nextfractal.mandelbrot.core.Expression.funcMod;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.ExpressionContext;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.InterpreterContext;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class InterpreterFuncMod implements CompiledExpression {
	private CompiledExpression[] arguments;
	private int index;
	
	public InterpreterFuncMod(ExpressionContext context, CompiledExpression[] arguments) {
		this.index = context.newNumberIndex();
		this.arguments = arguments;
	}

	@Override
	public double evaluateReal(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return funcMod(arguments[0].evaluate(context, scope));
	}

	@Override
	public Number evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		return context.getNumber(index).set(funcMod(arguments[0].evaluate(context, scope)));
	}

	@Override
	public boolean isReal() {
		return true;
	}
}
