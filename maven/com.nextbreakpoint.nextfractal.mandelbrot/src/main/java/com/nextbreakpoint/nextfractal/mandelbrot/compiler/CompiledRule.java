package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

public interface CompiledRule {
	public CompiledCondition getRuleCondition();

	public CompiledColorExpression getColorExp();

	public double getOpacity();
}
