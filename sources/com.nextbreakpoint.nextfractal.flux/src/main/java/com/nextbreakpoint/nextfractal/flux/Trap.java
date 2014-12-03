package com.nextbreakpoint.nextfractal.flux;

public class Trap {
	private String name;
	private Number center;
	
	public Trap(String name, Number center) {
		this.name = name;
		this.center = center;
	}
	
	public String getName() {
		return name;
	}

	public Number getCenter() {
		return center;
	}

	public void moveTo(Number x) {
	}

	public void lineTo(Number x) {
	}

	public void arcTo(Number x, Number y) {
	}

	public void moveRel(Number x) {
	}

	public void lineRel(Number x) {
	}

	public void arcRel(Number x, Number y) {
	}
	
	public boolean contains(Number z) {
		return false;
	}
}
