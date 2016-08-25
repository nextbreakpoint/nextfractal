/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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

public class Shape {
	protected int shapeType;
	protected Modification worldState;
	protected double areaCache;
	protected StackRule params;

	public Shape() {
		shapeType = -1;
		worldState = new Modification();
		areaCache = worldState.area();
	}

	public Shape(Shape shape) {
		shapeType = shape.shapeType;
		areaCache = shape.areaCache;
		worldState = shape.worldState;
		params = shape.params;
	}

	public StackRule getParameters() {
		return params;
	}

	public void setParameters(StackRule params) {
		this.params = params;
	}

	public int getShapeType() {
		return shapeType;
	}

	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
	}

	public Modification getWorldState() {
		return worldState;
	}

	public void setWorldState(Modification worldState) {
		this.worldState = worldState;
	}

	public double getArea() {
		return areaCache;
	}

	public void setArea(double area) {
		areaCache = area;
	}
}
