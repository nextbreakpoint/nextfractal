package com.nextbreakpoint.nextfractal.flux.mandelbrot.core;

@FunctionalInterface
public interface PaletteExpression {
	public double evaluate(double start, double end, double step);
}
