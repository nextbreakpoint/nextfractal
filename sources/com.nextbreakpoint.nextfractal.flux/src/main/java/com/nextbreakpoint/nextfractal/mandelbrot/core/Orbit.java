package com.nextbreakpoint.nextfractal.mandelbrot.core;

public abstract class Orbit {
	private final MutableNumber[] region = new MutableNumber[2];
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

	public Number[] region() {
		return region;
	}

	protected void setRegion(Number a, Number b) {
		this.region[0].set(a);
		this.region[1].set(b);
	}

	protected Trap trap(Number center) {
		return new Trap(center);
	}
	
	public abstract void init();

	public abstract void render();

	public void getState(MutableNumber[] state) {
		scope.getState(state);
	}

	public int stateSize() {
		return scope.stateSize();
	}

	public Variable getVariable(String name) {
		return scope.getVariable(name);
	}

	public void registerVariable(String name, Variable var) {
		scope.registerVariable(name, var);
	}

	public void addStateVariable(String name) {
		scope.addStateVariable(name);
	}

	public void removeStateVariable(String name) {
		scope.removeStateVariable(name);
	}
}
