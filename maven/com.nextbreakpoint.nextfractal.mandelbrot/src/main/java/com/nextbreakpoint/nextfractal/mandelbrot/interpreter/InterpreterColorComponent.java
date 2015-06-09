package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import java.util.Map;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerVariable;

public class InterpreterColorComponent implements CompiledColor {
	private CompiledExpression exp1;
	private CompiledExpression exp2;
	private CompiledExpression exp3;
	private CompiledExpression exp4;
	
	public InterpreterColorComponent(CompiledExpression exp1, CompiledExpression exp2, CompiledExpression exp3, CompiledExpression exp4) {
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.exp3 = exp3;
		this.exp4 = exp4;
	}

	@Override
	public float[] evaluate(InterpreterContext context, Map<String, CompilerVariable> scope) {
		if (exp1 != null && exp2 != null && exp2 != null && exp4 != null) {
			return new float[] { (float)exp1.evaluateReal(context, scope), (float)exp2.evaluateReal(context, scope), (float)exp3.evaluateReal(context, scope), (float)exp4.evaluateReal(context, scope) }; 
		} else if (exp1 != null && exp2 != null && exp2 != null) {
			return new float[] { (float)exp1.evaluateReal(context, scope), (float)exp2.evaluateReal(context, scope), (float)exp3.evaluateReal(context, scope), 1 }; 
		} else if (exp1 != null) {
			return new float[] { (float)exp1.evaluateReal(context, scope), (float)exp1.evaluateReal(context, scope), (float)exp1.evaluateReal(context, scope), 1 }; 
		}
		return new float[] { 0, 0, 0, 1 };
	}
}
