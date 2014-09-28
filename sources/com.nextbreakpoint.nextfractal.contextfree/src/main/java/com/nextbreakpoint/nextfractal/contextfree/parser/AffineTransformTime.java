package com.nextbreakpoint.nextfractal.contextfree.parser;

public class AffineTransformTime {
	private double step;
	private double begin;
	private double end;
	
	public AffineTransformTime() {
		this(0, 0, 1);
	}
	
	private AffineTransformTime(double step, double begin, double end) {
		this.step = step;
		this.begin = begin;
		this.end = end;
	}

	public static AffineTransformTime getTranslateInstance(double begin ,double end) {
		return new AffineTransformTime(1.0, begin, end);
	}
	
	public static AffineTransformTime getScaleInstance(double step) {
		return new AffineTransformTime(step, 0.0, 0.0);
	}

	public void preConcatenate(AffineTransformTime t) {
		this.step = this.step * t.step;
		this.begin = this.begin * t.step + t.begin;
		this.end = this.end * t.step + t.end;
	}
}
