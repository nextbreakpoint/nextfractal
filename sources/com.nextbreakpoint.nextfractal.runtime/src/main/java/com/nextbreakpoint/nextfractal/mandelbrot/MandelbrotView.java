package com.nextbreakpoint.nextfractal.mandelbrot;

import java.util.Arrays;

public class MandelbrotView {
	private final double[] traslation;
	private final double[] rotation;
	private final double[] scale;
	private final double[] point;
	private final boolean julia;

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale, double[] point, boolean julia) {
		this.traslation = traslation;
		this.rotation = rotation;
		this.scale = scale;
		this.point = point;
		this.julia = julia;
	}

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale, boolean julia) {
		this(traslation, rotation, scale, new double[] { 0, 0 }, julia);
	}

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale) {
		this(traslation, rotation, scale, new double[] { 0, 0 }, false);
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
	
	public boolean isJulia() {
		return julia;
	}
	
	public double[] getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "[traslation=" + Arrays.toString(traslation)	+ ", rotation=" + Arrays.toString(rotation) + ", scale=" + Arrays.toString(scale) + ", point=" + Arrays.toString(point) + ", julia=" + julia + "]";
	}
}
