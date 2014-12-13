package com.nextbreakpoint.nextfractal.flux.mandelbrot;

public abstract class Color {
	protected final float[] color = new float[] { 1f, 0f, 0f, 0f };

	protected float[] addColor(double opacity, float[] color) {
		for (int i = 0; i < 4; i++) {
			this.color[i] = (float)Math.min(1, this.color[i] + Math.max(0, color[i]) * opacity);
		}
		return color;
	}
	
	public float[] getColor() {
		return color;
	}

	public abstract void render();
}
