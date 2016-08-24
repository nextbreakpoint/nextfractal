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
import java.awt.geom.Point2D;

public class PathStorage {

	private int totalVertices;
	private boolean drawing;

	public void clear() {
		// TODO Auto-generated method stub
	}

	public int getTotalVertices() {
		return totalVertices;
	}

	public void endPoly(int flag) {
		// TODO Auto-generated method stub
	}

	public void startNewPath() {
		// TODO Auto-generated method stub
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public int command(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isVertex(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public void vertex(Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void closePolygon() {
		// TODO Auto-generated method stub
	}

	public void moveTo(Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void relToAbs(Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void lineTo(Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public int lastVertex(Point2D.Double point) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void arcTo(double radiusX, double radiusY, double angle, boolean largeArc, boolean sweep, Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void transform(AffineTransform inverseTr, int start) {
		// TODO Auto-generated method stub
	}

	public void modifyVertex(int start, Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public boolean isCurve(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public void curve3(Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void curve4(Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void curve3(Point2D.Double a, Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void curve3(Point2D.Double a, Point2D.Double b, Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void curve4(Point2D.Double a, Point2D.Double point) {
		// TODO Auto-generated method stub
	}

	public void curve4(Point2D.Double a, Point2D.Double b, Point2D.Double point) {
		// TODO Auto-generated method stub
	}
}
