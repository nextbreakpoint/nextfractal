package com.nextbreakpoint.nextfractal.flux;

public class Variable extends Number {
	private final String name;

	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}