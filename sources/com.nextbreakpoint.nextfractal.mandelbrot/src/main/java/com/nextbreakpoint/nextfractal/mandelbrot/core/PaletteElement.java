package com.nextbreakpoint.nextfractal.mandelbrot.core;

public class PaletteElement {
	private float[] beginColor;
	private float[] endColor;
	private int steps;
	private PaletteExpression expression;

	public PaletteElement(float[] beginColor, float[] endColor, int steps, PaletteExpression expression) {
		this.beginColor = beginColor;
		this.endColor = endColor;
		this.steps = steps;
		this.expression = expression;
	}

	public int getSteps() {
		return steps;
	}

	public float[] getBeginColor() {
		return beginColor;
	}

	public float[] getEndColor() {
		return endColor;
	}

	public PaletteExpression getExpression() {
		return expression;
	}
}