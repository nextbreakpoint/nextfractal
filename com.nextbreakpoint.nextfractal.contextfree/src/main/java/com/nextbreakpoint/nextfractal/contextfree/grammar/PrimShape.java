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

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrimShape {
	private GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);

	private static List<String> shapeNames = new ArrayList<String>();

	static {
		shapeNames.add("CIRCLE");
		shapeNames.add("FILL");
		shapeNames.add("SQUARE");
		shapeNames.add("TRIANGLE");
	}

	public static boolean isPrimShape(int shapeType) {
		return shapeType < 4;
	}

	public static Map<Integer, PrimShape> getShapeMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public int vertex(double[] x, double[] y) {
		// TODO Auto-generated method stub
		return 0;
	}

	public PathIterator getPathIterator() {
		return path.getPathIterator(new AffineTransform());
	}

	public static List<String> getShapeNames() {
		return shapeNames;
	}
}