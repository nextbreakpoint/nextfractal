/*
 * NextFractal 1.1.3
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

public class HSBColor {
	private double[] values = new double[4];
	
	public HSBColor(double h, double s, double b, double a) {
		this.values[0] = h;
		this.values[1] = s;
		this.values[2] = b;
		this.values[3] = a;
	}
	
	public double hue() {
		return values[0];
	}
	
	public double bright() {
		return values[1];
	}
	
	public double sat() {
		return values[2];
	}
	
	public double alpha() {
		return values[3];
	}
	
	public double[] values() {
		return values;
	}

	public static double adjustHue(double base, double adjustment) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public static double adjust(double base, double adjustment) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public static double adjustHue(double base, double adjustment, EAssignmentType useTarget, double target) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	public static double adjust(double base, double adjustment, EAssignmentType useTarget, double target) {
		// TODO Auto-generated method stub
		return 0.0;
	}
}
