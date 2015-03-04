package com.nextbreakpoint.nextfractal.mandelbrot.core;

public class PaletteElement {
	private int beginIndex;
	private int endIndex;
	private float[] beginColor;
	private float[] endColor;
	private PaletteExpression expression;

	public PaletteElement(int beginIndex, int endIndex, float[] beginColor, float[] endColor, PaletteExpression expression) {
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.beginColor = beginColor;
		this.endColor = endColor;
		this.expression = expression;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public int getEndIndex() {
		return endIndex;
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