package com.nextbreakpoint.nextfractal.mandelbrot;

import java.util.Arrays;

public class MandelbrotView {
	private double[] traslation;
	private double[] rotation;
	private double[] scale;

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale) {
		this.traslation = traslation;
		this.rotation = rotation;
		this.scale = scale;
	}

	public double[] getTraslation() {
		return traslation;
	}

	public double[] getRotation() {
		return rotation;
	}

	public double[] getScale() {
		return scale;
	}

	@Override
	public String toString() {
		return "[traslation=" + Arrays.toString(traslation)	+ ", rotation=" + Arrays.toString(rotation) + ", scale=" + Arrays.toString(scale) + "]";
	}
}
