package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;

public class RendererFractal {
	private final Scope scope = new Scope();
	private Orbit orbit;
	private Color color;

	public void initialize() {
		scope.empty();
		if (orbit != null) {
			orbit.init();
		}
	}

	public Orbit getOrbit() {
		return orbit;
	}

	public Color getColor() {
		return color;
	}

	public void setOrbit(Orbit orbit) {
		this.orbit = orbit;
		if (orbit != null) {
			orbit.setScope(scope);
		}
	}

	public void setColor(Color color) {
		this.color = color;
		if (color != null) {
			color.setScope(scope);
		}
	}

	/**
	 * @param state
	 * @param x
	 * @param w
	 */
	public void renderOrbit(MutableNumber[] state, Number x, Number w) {
		orbit.setX(x);
		orbit.setW(w);
		orbit.render();
		orbit.getState(state);
	}

	/**
	 * @param state
	 * @return
	 */
	public float[] renderColor(Number[] state) {
		color.setState(state);
		color.render();
		return color.getColor();
	}

	/**
	 * @return
	 */
	public int getStateSize() {
		return orbit.stateSize();
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
		orbit.setW(w);
	}

	/**
	 * @return
	 */
	public Number[] getRegion() {
		return orbit.region();
	}

	/**
	 * @param region
	 */
	public void setRegion(Number[] region) {
		orbit.setRegion(region);
	}

	/**
	 * @return
	 */
	public Number[] getInitialRegion() {
		return orbit.getInitialRegion();
	}

	/**
	 * 
	 */
	public void clearScope() {
		scope.clear();
	}
}
