package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

public interface RendererFractal {
	public Number[] renderOrbit(Number x, Number y);

	public float[] renderColor(Number[] o);

	public int getStateSize();

	public boolean isSolidGuessSupported();

	public boolean isVerticalSymetrySupported();

	public boolean isHorizontalSymetrySupported();

	public double getVerticalSymetryPoint();

	public double getHorizontalSymetryPoint();
}
