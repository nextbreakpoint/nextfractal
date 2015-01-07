package com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Number;

public class CompilerVariable extends Number {
	private final String name;
	private final boolean real;
	private final boolean create;

	public CompilerVariable(String name, boolean real, boolean create) {
		super(0, 0);
		this.name = name;
		this.real = real;
		this.create = create;
	}

	public String getName() {
		return name;
	}

	public boolean isReal() {
		return real;
	}

	public boolean isCreate() {
		return create;
	}
}