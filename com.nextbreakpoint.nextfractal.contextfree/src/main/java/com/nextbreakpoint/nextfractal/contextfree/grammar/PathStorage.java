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

import com.nextbreakpoint.nextfractal.contextfree.core.ExtendedGeneralPath;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class PathStorage {
	private ExtendedGeneralPath generalPath = new ExtendedGeneralPath();
	private boolean drawing;

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public void clear() {
		//TODO completare
	}

	public int getTotalVertices() {
		//TODO completare
		return 0;
	}

	public void endPoly(int flag) {
		//TODO completare
	}

	public void startNewPath() {
		//TODO completare
	}

	public int command(int index) {
		//TODO completare
		return 0;
	}

	public boolean isVertex(int index) {
		//TODO completare
		return false;
	}

	public void vertex(Point2D.Double point) {
		//TODO completare
	}

	public void closePolygon() {
		//TODO completare
	}

	public void moveTo(Point2D.Double point) {
		//TODO completare
	}

	public void relToAbs(Point2D.Double point) {
		//TODO completare
	}

	public void lineTo(Point2D.Double point) {
		//TODO completare
	}

	public int lastVertex(Point2D.Double point) {
		//TODO completare
		return 0;
	}

	public void arcTo(double radiusX, double radiusY, double angle, boolean largeArc, boolean sweep, Point2D.Double point) {
		//TODO completare
	}

	public void transform(AffineTransform t, int index) {
		//TODO completare
	}

	public void modifyVertex(int index, Point2D.Double point) {
		//TODO completare
	}

	public boolean isCurve(int i) {
		//TODO completare
		return false;
	}

	public void curve3(Point2D.Double point) {
		//TODO completare
	}

	public void curve4(Point2D.Double point) {
		//TODO completare
	}

	public void curve3(Point2D.Double a, Point2D.Double point) {
		//TODO completare
	}

	public void curve3(Point2D.Double a, Point2D.Double b, Point2D.Double point) {
		//TODO completare
	}

	public void curve4(Point2D.Double a, Point2D.Double point) {
		//TODO completare
	}

	public void curve4(Point2D.Double a, Point2D.Double b, Point2D.Double point) {
		//TODO completare
	}

	public PathIterator getPathIterator() {
		return generalPath.getPathIterator(new AffineTransform());
	}
}
