package com.nextbreakpoint.nextfractal.mandelbrot.core;

@FunctionalInterface
public interface PaletteExpression {
	public double evaluate(double start, double end, double step);
}
