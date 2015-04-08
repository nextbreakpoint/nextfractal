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
	private Number point;

	/**
	 * 
	 */
	public void initialize() {
		scope.empty();
		if (orbit != null) {
			orbit.init();
		}
	}

	/**
	 * @return
	 */
	public Orbit getOrbit() {
		return orbit;
	}

	/**
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param orbit
	 */
	public void setOrbit(Orbit orbit) {
		this.orbit = orbit;
		if (orbit != null) {
			orbit.setScope(scope);
		}
	}

	/**
	 * @param color
	 */
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
		orbit.render(null);
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
	 * 
	 */
	public void clearScope() {
		scope.clear();
	}

	/**
	 * @return
	 */
	public Number getPoint() {
		return point;
	}

	/**
	 * @param point
	 */
	public void setPoint(Number point) {
		this.point = point;
	}
}
