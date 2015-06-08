package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;

public interface InterpreterContext {
	public MutableNumber getVariable(String name);

	public MutableNumber getNumber(int index);
}
