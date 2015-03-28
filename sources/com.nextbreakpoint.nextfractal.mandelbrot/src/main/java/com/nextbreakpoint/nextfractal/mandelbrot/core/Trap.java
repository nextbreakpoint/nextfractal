package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.awt.geom.Path2D;

public class Trap {
	private final Path2D.Double path2d = new Path2D.Double();
	private final Number center;
	
	public Trap(Number center) {
		this.center = center;
	}
	
	public Number getCenter() {
		return center;
	}

	public Trap moveTo(Number x) {
		path2d.moveTo(x.r(), x.i());
		return this;
	}

	public Trap lineTo(Number x) {
		path2d.lineTo(x.r(), x.i());
		return this;
	}

	public Trap arcTo(Number x, Number y) {
		path2d.quadTo(x.r(), x.i(), y.r(), y.i());
		return this;
	}

	public Trap moveRel(Number x) {
		path2d.moveTo(x.r(), x.i());
		return this;
	}

	public Trap lineRel(Number x) {
		path2d.lineTo(x.r(), x.i());
		return this;
	}

	public Trap arcRel(Number x, Number y) {
		path2d.quadTo(x.r(), x.i(), y.r(), y.i());
		return this;
	}
	
	public boolean contains(Number x) {
		return path2d.contains(x.r() - center.r(), x.i() - center.i());
	}
}
