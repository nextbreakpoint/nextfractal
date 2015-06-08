package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColor;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledExpression;

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
	public float[] evaluate(InterpreterContext context) {
		if (exp1 != null && exp2 != null && exp2 != null && exp4 != null) {
			return new float[] { (float)exp1.evaluateReal(context), (float)exp2.evaluateReal(context), (float)exp3.evaluateReal(context), (float)exp4.evaluateReal(context) }; 
		} else if (exp1 != null && exp2 != null && exp2 != null) {
			return new float[] { (float)exp1.evaluateReal(context), (float)exp2.evaluateReal(context), (float)exp3.evaluateReal(context), 1 }; 
		} else if (exp1 != null) {
			return new float[] { (float)exp1.evaluateReal(context), (float)exp1.evaluateReal(context), (float)exp1.evaluateReal(context), 1 }; 
		}
		return new float[] { 0, 0, 0, 1 };
	}
}
