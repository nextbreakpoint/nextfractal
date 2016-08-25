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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimShape extends PathStorage {
	private static List<String> shapeNames = new ArrayList<>();
	private static Map<Integer, PrimShape> shapeMap = new HashMap<>();

	static {
		shapeNames.add("CIRCLE");
		shapeNames.add("SQUARE");
		shapeNames.add("TRIANGLE");
		shapeNames.add("FILL");

		double h = 0.5 / Math.cos(Math.PI/6.0);
		double hp = h;
		double hn = -h / 2.0;
		double t = Math.sqrt(2.0) / 4.0;

		PrimShape circle = new PrimShape();
		PrimShape square = new PrimShape();
		PrimShape triangle = new PrimShape();

		circle.vertex(new Point2D.Double(0.5, 0.0));
		circle.vertex(new Point2D.Double(t, t));
		circle.vertex(new Point2D.Double(0.0, 0.5));
		circle.vertex(new Point2D.Double(-t, t));
		circle.vertex(new Point2D.Double(-0.5, 0.0));
		circle.vertex(new Point2D.Double(-t, -t));
		circle.vertex(new Point2D.Double(0.0, -0.5));
		circle.vertex(new Point2D.Double(t, -t));

		square.vertex(new Point2D.Double(0.5, 0.5));
		square.vertex(new Point2D.Double(-0.5, 0.5));
		square.vertex(new Point2D.Double(-0.5, -0.5));
		square.vertex(new Point2D.Double(0.5, -0.5));

		triangle.vertex(new Point2D.Double(0.0, hp));
		triangle.vertex(new Point2D.Double(-0.5, hn));
		triangle.vertex(new Point2D.Double(0.5, hn));

		shapeMap.put(0, circle);
		shapeMap.put(1, square);
		shapeMap.put(2, triangle);
	}

	public static boolean isPrimShape(int shapeType) {
		return shapeType < 4;
	}

	public static Map<Integer, PrimShape> getShapeMap() {
		return shapeMap;
	}

	public static List<String> getShapeNames() {
		return shapeNames;
	}
}
