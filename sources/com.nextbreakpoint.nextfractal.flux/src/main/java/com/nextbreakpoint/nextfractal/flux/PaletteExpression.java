package com.nextbreakpoint.nextfractal.flux;

@FunctionalInterface
public interface PaletteExpression {
	public double evaluate(int start, int end, int step);
}
