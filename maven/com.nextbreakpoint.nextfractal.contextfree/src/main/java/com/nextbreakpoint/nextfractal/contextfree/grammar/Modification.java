/*
 * NextFractal 1.1.2
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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.awt.geom.AffineTransform;

class Modification {
	private Rand64 rand64Seed = new Rand64();
	private AffineTransform transform = new AffineTransform();
	private AffineTransform1D transformZ = new AffineTransform1D();
	private AffineTransformTime transformTime = new AffineTransformTime();
	private HSBColor color;
	private HSBColor colorTarget;

	public Rand64 getRand64Seed() {
		return rand64Seed;
	}

	public void setRand64Seed(Rand64 rand64Seed) {
		this.rand64Seed = rand64Seed;
	}

	public void setSeed(int i) {
		rand64Seed.setSeed(i);
	}

	public boolean merge(Modification mod) {
		// TODO Auto-generated method stub
		return false;
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public AffineTransform1D getTransformZ() {
		return transformZ;
	}

	public AffineTransformTime getTransformTime() {
		return transformTime;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}
	
	public void setTransformZ(AffineTransform1D transformZ) {
		this.transformZ = transformZ;
	}

	public void setTransformTime(AffineTransformTime transformTime) {
		this.transformTime = transformTime;
	}

	public HSBColor color() {
		return color;
	}

	public HSBColor colorTarget() {
		return colorTarget;
	}

	public long colorAssignment() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setColorAssignment(long assignment) {
	}

	public double getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setZ(double d) {
		// TODO Auto-generated method stub
	}

	public double area() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public void concat(Modification modData) {
		// TODO Auto-generated method stub
		
	}
}
