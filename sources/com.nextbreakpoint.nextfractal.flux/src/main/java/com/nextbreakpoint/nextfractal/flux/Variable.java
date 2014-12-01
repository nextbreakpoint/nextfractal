package com.nextbreakpoint.nextfractal.flux;

public class Variable extends Number {
	private final String name;
	private final boolean real;

	public Variable(String name, boolean real) {
		super(0, 0);
		this.name = name;
		this.real = real;
	}

	public String getName() {
		return name;
	}

	public boolean isReal() {
		return real;
	}
}