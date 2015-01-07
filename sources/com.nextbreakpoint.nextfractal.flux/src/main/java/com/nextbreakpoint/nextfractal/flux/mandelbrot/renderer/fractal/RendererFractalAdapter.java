package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.fractal;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.Fractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public class RendererFractalAdapter implements RendererFractal {
	private final Fractal fractal;

	public RendererFractalAdapter(Fractal fractal) {
		this.fractal = fractal;
	}

	@Override
	public Number[] renderOrbit(Number x, Number y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] renderColor(Number[] o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStateSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSolidGuessSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVerticalSymetrySupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHorizontalSymetrySupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getVerticalSymetryPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHorizontalSymetryPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setConstant(Number w) {
		// TODO Auto-generated method stub

	}

}
