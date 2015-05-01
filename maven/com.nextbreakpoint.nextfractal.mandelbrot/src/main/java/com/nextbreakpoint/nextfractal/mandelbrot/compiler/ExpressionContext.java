package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

public class ExpressionContext {
	private int numberCount;
	
	public int newNumberIndex() {
		return numberCount++;
	}

	public int getNumberCount() {
		return numberCount;
	}
}
