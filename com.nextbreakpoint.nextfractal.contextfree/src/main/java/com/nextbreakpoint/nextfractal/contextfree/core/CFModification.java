/*
 * NextFractal 1.2
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
package com.nextbreakpoint.nextfractal.contextfree.core;

import java.awt.geom.AffineTransform;

public class CFModification implements Cloneable {
	private AffineTransform transform;
	private CFColor color;
	private CFColor colorTarget;
	private float sizeZ = 1;
	private float z = 0;

	public CFModification() {
		this.transform = new AffineTransform();
		this.color = new CFColor();
		this.colorTarget = new CFColor();
		this.z = 0;
	}

	public CFModification(AffineTransform transform) {
		this.transform = transform;
		this.color = new CFColor();
		this.colorTarget = new CFColor();
		this.z = 0;
	}

	public CFModification(AffineTransform transform, CFColor color, CFColor colorTarget, float z) {
		this.transform = transform;
		this.color = color;
		this.colorTarget = colorTarget;
		this.z = z;
	}

	public void translate(float tx, float ty, float tz) {
		transform.translate(tx, ty);
		z += tz;
	}

	public void skew(float sx, float sy) {
		double rsx = Math.toRadians(sx); 
		double rsy = Math.toRadians(sy); 
		transform.shear(rsx, rsy);
	}

	public void scale(float sx, float sy, float sz) {
		transform.scale(sx, sy);
	}

	public void flip(float a) {
		double ra = Math.toRadians(a); 
		transform.rotate(+ra);
		transform.scale(1, -1);
		transform.rotate(-ra);
	}

	public void rotate(float a) {
		double ra = Math.toRadians(a); 
		transform.rotate(ra);
	}
	
	public void transform(float[] p, float[] q) {
		transform.transform(p, 0, q, 0, p.length / 2);
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public float getZ() {
		return z;
	}
	
	public CFColor getColor() {
		return color;
	}
	
	public CFColor getColorTarget() {
		return colorTarget;
	}

	@Override
	public CFModification clone() {
		CFModification modification = new CFModification((AffineTransform) transform.clone());
		modification.color = color.clone();
		modification.colorTarget = colorTarget.clone();
		modification.z = z;
		modification.sizeZ = sizeZ;
		return modification;
	}

	public void concatenate(CFModification modification) {
		transform.concatenate(modification.transform);
		z += modification.z * sizeZ;
		sizeZ *= modification.sizeZ;
		color.adjustWith(modification.color, colorTarget);
		colorTarget.adjustWith(modification.colorTarget, colorTarget);
	}

	@Override
	public String toString() {
		return "CFModification [transform=" + transform + ", color=" + color + ", colorTarget=" + colorTarget + ", z=" + z + ", sizeZ=" + sizeZ + "]";
	}
}
