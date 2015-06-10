package com.nextbreakpoint.nextfractal.mandelbrot.compiler.support;

public class CompiledPaletteElement {
	private float[] beginColor;
	private float[] endColor;
	private int steps;
	private CompiledExpression exp;

	public CompiledPaletteElement(float[] beginColor, float[] endColor,	int steps, CompiledExpression exp) {
		this.beginColor = beginColor;
		this.endColor = endColor;
		this.steps = steps;
		this.exp = exp;
	}

	public float[] getBeginColor() {
		return beginColor;
	}

	public float[] getEndColor() {
		return endColor;
	}

	public int getSteps() {
		return steps;
	}

	public CompiledExpression getExp() {
		return exp;
	}
}
