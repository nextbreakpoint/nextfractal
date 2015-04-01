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

	public Trap arcTo(Number x) {
		path2d.quadTo(path2d.getCurrentPoint().getX(), path2d.getCurrentPoint().getY(), x.r(), x.i());
		return this;
	}

	public Trap curveTo(Number x, Number y) {
		path2d.quadTo(x.r(), x.i(), y.r(), y.i());
		return this;
	}

	public Trap moveToRel(Number x) {
		path2d.moveTo(path2d.getCurrentPoint().getX() + x.r(), path2d.getCurrentPoint().getY() + x.i());
		return this;
	}

	public Trap lineToRel(Number x) {
		path2d.lineTo(path2d.getCurrentPoint().getX() + x.r(), path2d.getCurrentPoint().getY() + x.i());
		return this;
	}

	public Trap arcToRel(Number x) {
		path2d.quadTo(path2d.getCurrentPoint().getX(), path2d.getCurrentPoint().getY(), path2d.getCurrentPoint().getX() + x.r(), path2d.getCurrentPoint().getY() + x.i());
		return this;
	}

	public Trap curveToRel(Number x, Number y) {
		path2d.quadTo(path2d.getCurrentPoint().getX() + x.r(), path2d.getCurrentPoint().getY() + x.i(), path2d.getCurrentPoint().getX() + y.r(), path2d.getCurrentPoint().getY() + y.i());
		return this;
	}

	public boolean contains(Number x) {
		return path2d.contains(x.r() - center.r(), x.i() - center.i());
	}
}
