package com.nextbreakpoint.nextfractal.flux.mandelbrot.core;


public abstract class Orbit {
	private final MutableNumber[] region = new MutableNumber[2];
	protected Number x = new Number(0,0);
	protected Number w = new Number(0,0);
	protected Number z = new Number(0,0);
	protected Number n = new Number(0);
	protected Number c = new Number(0);

	public Orbit() {
		region[0] = new MutableNumber();
		region[1] = new MutableNumber();
	}
	
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

	public Number[] region() {
		return region;
	}

	protected void setRegion(Number a, Number b) {
		this.region[0].set(a);
		this.region[1].set(b);
	}
	
	public abstract void render();
}
