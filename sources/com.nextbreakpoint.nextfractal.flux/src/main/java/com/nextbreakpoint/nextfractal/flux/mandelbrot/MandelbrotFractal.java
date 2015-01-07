package com.nextbreakpoint.nextfractal.flux.mandelbrot;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Fractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public class MandelbrotFractal implements RendererFractal {
	private final Fractal fractal;

	public MandelbrotFractal(Fractal fractal) {
		this.fractal = fractal;
	}

	public Number[] renderOrbit(Number x, Number y) {
		// TODO Auto-generated method stub
		return null;
	}

	public float[] renderColor(Number[] o) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStateSize() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub

	}

}
