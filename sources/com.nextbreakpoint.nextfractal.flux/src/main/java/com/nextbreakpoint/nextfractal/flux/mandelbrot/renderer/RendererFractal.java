package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;

public interface RendererFractal {
	public Number[] renderOrbit(Number x, Number y);

	public float[] renderColor(Number[] o);

	public int getStateSize();

	public boolean isSolidGuessSupported();

	public boolean isVerticalSymetrySupported();

	public boolean isHorizontalSymetrySupported();

	public double getVerticalSymetryPoint();

	public double getHorizontalSymetryPoint();

	public void setConstant(Number w);
}
