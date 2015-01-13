package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Fractal;

public class RendererFractal {
	private final Fractal fractal;

	public RendererFractal(Fractal fractal) {
		this.fractal = fractal;
		if (fractal == null) {
			throw new IllegalArgumentException("Fractal cannot be null");
		}
	}

	public Number[] renderOrbit(Number x, Number w) {
		fractal.setX(x);
		fractal.setW(w);
		fractal.renderOrbit();
		Number[] state = fractal.state();
		return state;
	}

	public float[] renderColor(Number[] state) {
		fractal.setState(state);
		fractal.renderColor();
		float[] color = fractal.getColor();
		return color;
	}

	public int getStateSize() {
		return fractal.stateSize();
	}

	public boolean isSolidGuessSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isVerticalSymetrySupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isHorizontalSymetrySupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public double getVerticalSymetryPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getHorizontalSymetryPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setConstant(Number w) {
		fractal.setW(w);
	}

	public Number[] getRegion() {
		return fractal.getRegion();
	}
}
