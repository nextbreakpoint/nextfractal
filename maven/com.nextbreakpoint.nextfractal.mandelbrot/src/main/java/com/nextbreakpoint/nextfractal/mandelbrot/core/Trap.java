/*
 * NextFractal 1.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
