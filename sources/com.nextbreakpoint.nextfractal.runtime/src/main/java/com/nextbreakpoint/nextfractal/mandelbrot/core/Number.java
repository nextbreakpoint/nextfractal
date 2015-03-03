package com.nextbreakpoint.nextfractal.mandelbrot.core;

import javax.xml.bind.annotation.XmlElement;

public class Number {
	protected double r;
	protected double i;

	public Number() {
		this(0, 0);
	}

	public Number(int n) {
		this(n, 0);
	}

	public Number(double r) {
		this(r, 0);
	}

	public Number(double[] v) {
		this(v[0], v[1]);
	}

	public Number(Number number) {
		this(number.r(), number.i());
	}
	
	public Number(double r, double i) {
		this.r = r;
		this.i = i;
	}

	public double r() {
		return r;
	}

	public double i() {
		return i;
	}

	public int n() {
		return (int)r;
	}

	public boolean isReal() {
		return i == 0;
	}

	public boolean isInteger() {
		return i == 0 && r == (int)r;
	}

	public void set(Number x) {
		this.r = x.r;
		this.i = x.i;
	}

	@Override
	public String toString() {
		return r + ", " + i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(i);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(r);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Number other = (Number) obj;
		if (Double.doubleToLongBits(i) != Double.doubleToLongBits(other.i))
			return false;
		if (Double.doubleToLongBits(r) != Double.doubleToLongBits(other.r))
			return false;
		return true;
	}
}