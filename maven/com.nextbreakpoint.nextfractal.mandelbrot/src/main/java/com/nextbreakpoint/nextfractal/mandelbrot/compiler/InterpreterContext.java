package com.nextbreakpoint.nextfractal.mandelbrot.compiler;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public interface InterpreterContext {
	public MutableNumber getNumber(int index);

	public boolean isJulia();

	public Trap getTrap(String name);

	public Palette getPalette(String name);
}
