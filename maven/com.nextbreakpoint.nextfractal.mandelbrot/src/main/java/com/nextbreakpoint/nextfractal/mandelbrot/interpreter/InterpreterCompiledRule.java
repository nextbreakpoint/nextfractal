package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledColorExpression;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledCondition;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompiledRule;

public class InterpreterCompiledRule implements CompiledRule {
	private CompiledCondition ruleCondition;
	private CompiledColorExpression colorExp;
	private double opacity;

	public CompiledCondition getRuleCondition() {
		return ruleCondition;
	}

	public void setRuleCondition(CompiledCondition ruleCondition) {
		this.ruleCondition = ruleCondition;
	}

	public CompiledColorExpression getColorExp() {
		return colorExp;
	}

	public void setColorExp(CompiledColorExpression colorExp) {
		this.colorExp = colorExp;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
}
