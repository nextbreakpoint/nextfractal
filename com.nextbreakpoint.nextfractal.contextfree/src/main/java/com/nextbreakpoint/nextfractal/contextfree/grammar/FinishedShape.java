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

import com.nextbreakpoint.nextfractal.contextfree.core.Bounds;

public class FinishedShape extends Shape implements Comparable<FinishedShape> {
	private Bounds bounds;

	public FinishedShape(Shape shape, int order, Bounds bounds) {
		// TODO completare
		super(shape);
		getWorldState().setColorAssignment(order);
		this.bounds = bounds;
	}

	@Override
	public int compareTo(FinishedShape o) {
		if (worldState.getTransformZ().tz() == o.worldState.getTransformZ().tz()) {
			return (int)(worldState.colorAssignment() - o.worldState.colorAssignment());
		} else {
			return worldState.getTransformZ().tz() - o.worldState.getTransformZ().tz() < 0 ? -1 : 1;
		}
	}

	public Bounds bounds() {
		return bounds;
	}
}