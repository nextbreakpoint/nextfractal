package com.nextbreakpoint.nextfractal.flux.mandelbrot;

public class MutableNumber extends Number {
	public MutableNumber(int n) {
		super(n, 0);
	}

	public MutableNumber(double r) {
		super(r, 0);
	}

	public MutableNumber(double r, double i) {
		super(r, i);
	}

	public MutableNumber() {
		super(0, 0);
	}
	
	public void set(double r, double i) {
		this.r = r;
		this.i = i;
	}
	
	public void set(double r) {
		this.r = r;
		this.i = 0;
	}
	
	public void set(int n) {
		this.r = n;
		this.i = 0;
	}
}