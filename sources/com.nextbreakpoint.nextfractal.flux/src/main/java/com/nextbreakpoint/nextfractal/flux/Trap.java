package com.nextbreakpoint.nextfractal.flux;

public class Trap {
	private Number center;
	
	public Trap(Number center) {
		this.center = center;
	}
	
	public Number getCenter() {
		return center;
	}

	public Trap moveTo(Number x) {
		return this;
	}

	public Trap lineTo(Number x) {
		return this;
	}

	public Trap arcTo(Number x, Number y) {
		return this;
	}

	public Trap moveRel(Number x) {
		return this;
	}

	public Trap lineRel(Number x) {
		return this;
	}

	public Trap arcRel(Number x, Number y) {
		return this;
	}
	
	public boolean contains(Number z) {
		return false;
	}
}
