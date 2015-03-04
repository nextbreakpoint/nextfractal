package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.List;


public abstract class Orbit {
	protected Number[] region = new Number[2];
	protected Number point = new Number(0, 0);
	protected Number x = new Number(0,0);
	protected Number w = new Number(0,0);
	protected Number z = new Number(0,0);
	protected Number n = new Number(0);
	protected Number c = new Number(0);
	protected Scope scope;

	public Orbit() {
		region[0] = new MutableNumber();
		region[1] = new MutableNumber();
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
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

	protected Trap trap(Number center) {
		return new Trap(center);
	}
	
	public abstract void init();

	public abstract void render(List<Number[]> states);

	public void getState(MutableNumber[] state) {
		scope.getState(state);
	}

	public int stateSize() {
		return scope.stateSize();
	}

	public Number getVariable(int index) {
		return scope.getVariable(index);
	}

	public void setVariable(int index, Number value) {
		scope.setVariable(index, value);
	}

	public void createVariable(Number value) {
		scope.createVariable(value);
	}

	public Number[] getInitialRegion() {
		return region;
	}

	public Number getInitialPoint() {
		return point;
	}

	public void setInitialRegion(Number a, Number b) {
		this.region[0].set(a);
		this.region[1].set(b);
	}
}
