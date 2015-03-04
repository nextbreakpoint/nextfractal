package com.nextbreakpoint.nextfractal.renderer;

public class RendererSize {
	private int width;
	private int height;

	public RendererSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "[width=" + width + ", height=" + height + "]";
	}
}
