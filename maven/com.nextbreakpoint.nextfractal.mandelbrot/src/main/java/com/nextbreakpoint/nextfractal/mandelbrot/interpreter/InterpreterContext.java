package com.nextbreakpoint.nextfractal.mandelbrot.interpreter;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;

public interface InterpreterContext {
	public MutableNumber getVariable(String name);

	public MutableNumber getNumber(int index);

	public boolean isJulia();

	public void stop();

	public void addVariable(MutableNumber var, String name, boolean real, boolean b);

	public Palette getPalette(String name);

	public Trap getTrap(String name);
}
