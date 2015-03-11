package com.nextbreakpoint.nextfractal.mandelbrot.core;

public abstract class Color {
	protected final float[] color = new float[] { 1f, 0f, 0f, 0f };
	protected Scope scope;

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Number getVariable(int index) {
		return scope.getVariable(index);
	}

	protected float[] setColor(float[] color) {
		for (int i = 0; i < 4; i++) {
			this.color[i] = (float)Math.min(1, Math.max(0, color[i]));
		}
		return color;
	}
	
	protected float[] addColor(double opacity, float[] color) {
		for (int i = 0; i < 4; i++) {
			this.color[i] = (float)Math.min(1, this.color[i] + Math.max(0, color[i]) * opacity);
		}
		return color;
	}
	
	public float[] getColor() {
		return color;
	}

	protected Palette palette() {
		return new Palette();
	}

	protected PaletteElement element(float[] beginColor, float[] endColor, int steps, PaletteExpression expression) {
		return new PaletteElement(beginColor, endColor, steps, expression);
	}

	protected float[] color(double x) {
		return new float[] { 1f, (float) x, (float) x, (float) x };
	}

	protected float[] color(double r, double g, double b) {
		return new float[] { 1f, (float) r, (float) g, (float) b };
	}

	protected float[] color(double a, double r, double g, double b) {
		return new float[] { (float) a, (float) r, (float) g, (float) b };
	}

	public abstract void render();

	public void setState(Number[] state) {
		scope.setState(state);
	}
}
