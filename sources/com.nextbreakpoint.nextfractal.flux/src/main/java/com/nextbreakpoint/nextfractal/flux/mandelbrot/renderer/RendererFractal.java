package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Fractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Number;

public class RendererFractal {
	private final Fractal fractal;

	/**
	 * @param fractal
	 */
	public RendererFractal(Fractal fractal) {
		this.fractal = fractal;
		if (fractal == null) {
			throw new IllegalArgumentException("Fractal cannot be null");
		}
	}

	/**
	 * @param state
	 * @param x
	 * @param w
	 */
	public void renderOrbit(MutableNumber[] state, Number x, Number w) {
		fractal.setX(x);
		fractal.setW(w);
		fractal.renderOrbit();
		fractal.getState(state);
	}

	/**
	 * @param state
	 * @return
	 */
	public float[] renderColor(Number[] state) {
		fractal.setState(state);
		fractal.renderColor();
		float[] color = fractal.getColor();
		return color;
	}

	/**
	 * @return
	 */
	public int getStateSize() {
		return fractal.stateSize();
	}

	/**
	 * @return
	 */
	public boolean isSolidGuessSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return
	 */
	public boolean isVerticalSymetrySupported() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return
	 */
	public boolean isHorizontalSymetrySupported() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return
	 */
	public double getVerticalSymetryPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return
	 */
	public double getHorizontalSymetryPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param w
	 */
	public void setConstant(Number w) {
		fractal.setW(w);
	}

	/**
	 * @return
	 */
	public Number[] getRegion() {
		return fractal.getRegion();
	}
}
