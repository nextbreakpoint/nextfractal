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

public class CFShape implements Cloneable {
	private CFModification modification;
	private int initialShapeType;
	private double area;

	public CFShape(int initialShapeType, CFModification modification) {
		this.modification = modification;
		this.initialShapeType = initialShapeType;
		area = Math.abs(modification.getTransform().getDeterminant());
	}

	public final int getInitialShapeType() {
		return initialShapeType;
	}

	public CFModification getModification() {
		return modification;
	}
	
	public double area() {
		return area;
	}

	@Override
	public CFShape clone() {
		return new CFShape(initialShapeType, modification.clone());
	}

	public void concatenate(CFReplacement replacement) {
		initialShapeType = replacement.getShapeType();
		modification.concatenate(replacement.getModification());
		area = Math.abs(modification.getTransform().getDeterminant());
	}

	@Override
	public String toString() {
		return "CFShape [initialShapeType=" + initialShapeType + ", modification=" + modification + ", area=" + area + "]";
	}
}
