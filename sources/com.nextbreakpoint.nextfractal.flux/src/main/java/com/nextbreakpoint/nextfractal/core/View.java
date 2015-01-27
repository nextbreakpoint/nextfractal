package com.nextbreakpoint.nextfractal.core;

public class View {
	private DoubleVector4D traslation = new DoubleVector4D(0, 0, 0, 0);
	private DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
	private DoubleVector4D scale = new DoubleVector4D(1, 1, 1, 1);

	public View() {
		traslation = new DoubleVector4D(0, 0, 0, 0);
		rotation = new DoubleVector4D(0, 0, 0, 0);
		scale = new DoubleVector4D(1, 1, 1, 1);
	}

	public View(DoubleVector4D traslation, DoubleVector4D rotation,	DoubleVector4D scale) {
		this.traslation = traslation;
		this.rotation = rotation;
		this.scale = scale;
	}

	public DoubleVector4D getTraslation() {
		return traslation;
	}

	public DoubleVector4D getRotation() {
		return rotation;
	}

	public DoubleVector4D getScale() {
		return scale;
	}

	@Override
	public String toString() {
		return "[traslation=" + traslation + ", rotation=" + rotation + ", scale=" + scale + "]";
	}
}
