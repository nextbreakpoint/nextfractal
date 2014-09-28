package com.nextbreakpoint.nextfractal.contextfree.parser;

public class AffineTransform1D {
	private double sz;
	private double tz;
	
	private AffineTransform1D(double sz, double tz) {
		this.tz = tz;
	}

	public AffineTransform1D() {
		this(1, 0);
	}

	public static AffineTransform1D getTranslateInstance(double tz) {
		return new AffineTransform1D(1.0, tz);
	}
	
	public static AffineTransform1D getScaleInstance(double sz) {
		return new AffineTransform1D(sz, 0.0);
	}

	public void preConcatenate(AffineTransform1D t) {
		this.sz = this.sz * t.sz;
		this.tz = this.tz * t.sz + t.tz;
	}
}
