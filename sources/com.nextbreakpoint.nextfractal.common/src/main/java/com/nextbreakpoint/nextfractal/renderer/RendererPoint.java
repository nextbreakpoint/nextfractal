package com.nextbreakpoint.nextfractal.renderer;

public class RendererPoint {
	private int x;
	private int y;

	public RendererPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
}
