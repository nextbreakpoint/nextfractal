package com.nextbreakpoint.nextfractal.flux;

public class Variable extends Number {
	private final String name;

	public Variable(String name) {
		super(0, 0);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}