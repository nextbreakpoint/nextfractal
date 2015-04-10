/*
 * NextFractal 1.0
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

public class CFFinishedShape implements Cloneable, Comparable<CFFinishedShape> {
	private CFPath path;
	private CFPathAttribute attribute;
	private double cumulativeArea;
	private boolean dirty = true;

	public CFFinishedShape(CFPath path, CFPathAttribute attribute) {
		this(path, attribute, 0);
	}

	public CFFinishedShape(CFPath path, CFPathAttribute attribute, double cumulativeArea) {
		this.path = path;
		this.attribute = attribute;
		this.cumulativeArea = cumulativeArea;
	}

	public double getCumulativeArea() {
		return cumulativeArea;
	}
	
	@Override
	public CFFinishedShape clone() {
		return new CFFinishedShape(path.clone(), attribute.clone(), cumulativeArea);
	}

	@Override
	public int compareTo(CFFinishedShape s) {
		if (attribute.getModification().getZ() == s.attribute.getModification().getZ()) {
			return (cumulativeArea - s.cumulativeArea) < 0 ? -1 : 1;
		}
		return (attribute.getModification().getZ() - s.attribute.getModification().getZ()) < 0 ? -1 : 1;
	}

	@Override
	public String toString() {
		return "CFFinishedShape [path=" + path + ", attribute=" + attribute + ", cumulativeArea=" + cumulativeArea + "]";
	}

	public void render(CFShapeRenderer renderer) {
		renderer.render(path, attribute);
		dirty = false;
	}

	public double area() {
		return attribute.getModification().getTransform().getDeterminant();
	}

	public boolean isDirty() {
		return dirty;
	}
}
