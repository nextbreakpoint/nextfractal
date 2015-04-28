/*
 * NextFractal 1.0.2
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

public class CFRuleSpecifier implements Comparable<CFRuleSpecifier> {
	protected int initialShapeType;
	protected double weight;

	public CFRuleSpecifier(int initialShapeType, double weight) {
		this.initialShapeType = initialShapeType;
		this.weight = weight;
	}

	public int getInitialShapeType() {
		return initialShapeType;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public int compareTo(CFRuleSpecifier s) {
		if (initialShapeType == s.initialShapeType) {
			return (weight - s.weight) < 0 ? -1 : +1;
		} else {
			return initialShapeType - s.initialShapeType;
		}
	}

	@Override
	public String toString() {
		return "CFRuleSpecifier [initialShapeType=" + initialShapeType + ", weight=" + weight + "]";
	}
}
