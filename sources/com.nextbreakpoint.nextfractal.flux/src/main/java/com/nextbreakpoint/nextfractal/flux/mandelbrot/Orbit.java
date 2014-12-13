package com.nextbreakpoint.nextfractal.flux.mandelbrot;

public abstract class Orbit {
	protected Number x = new Number(0,0);
	protected Number w = new Number(0,0);
	protected Number z = new Number(0,0);
	protected Number n = new Number(0);
	protected Number c = new Number(0);

	public void setX(Number x) {
		this.x = x;
	}

	public void setW(Number w) {
		this.w = w;
	}

	public Number getX() {
		return x;
	}

	public Number getW() {
		return w;
	}

	public Number getZ() {
		return z;
	}

	public Number getN() {
		return n;
	}

	public Number getC() {
		return c;
	}

	public abstract void render();
}
