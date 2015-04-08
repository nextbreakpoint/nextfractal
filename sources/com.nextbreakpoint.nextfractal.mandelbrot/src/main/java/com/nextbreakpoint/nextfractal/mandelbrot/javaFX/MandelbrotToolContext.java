package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public interface MandelbrotToolContext {
	public double getWidth();

	public double getHeight();

	public MandelbrotSession getMandelbrotSession();

	public Number getInitialSize();

	public Number getInitialCenter();

	public double getZoomSpeed();
}
