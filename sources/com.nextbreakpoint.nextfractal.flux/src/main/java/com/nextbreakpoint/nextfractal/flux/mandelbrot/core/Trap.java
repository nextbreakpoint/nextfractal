package com.nextbreakpoint.nextfractal.flux.mandelbrot.core;


public class Trap {
	private Number center;
	
	public Trap(Number center) {
		this.center = center;
	}
	
	public Number getCenter() {
		return center;
	}

	public Trap moveTo(Number x) {
		//TODO moveTo
		return this;
	}

	public Trap lineTo(Number x) {
		//TODO lineTo
		return this;
	}

	public Trap arcTo(Number x, Number y) {
		//TODO arcTo
		return this;
	}

	public Trap moveRel(Number x) {
		//TODO moveRel
		return this;
	}

	public Trap lineRel(Number x) {
		//TODO lineRel
		return this;
	}

	public Trap arcRel(Number x, Number y) {
		//TODO arcRel
		return this;
	}
	
	public boolean contains(Number z) {
		//TODO contains
		return false;
	}
}
