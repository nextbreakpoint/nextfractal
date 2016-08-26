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
	private int totalVerices;
	private Point2D.Double center = new Point2D.Double(0, 0);

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	public void clear() {
		generalPath.reset();
	}

	public void startNewPath() {
		generalPath = new ExtendedGeneralPath();
	}

	public void closePolygon() {
		generalPath.closePath();
	}

	public void moveTo(Point2D.Double point) {
		center.setLocation(point.getX(), point.getY());
		generalPath.moveTo((float)point.getX(), (float)point.getY());
	}

	public void lineTo(Point2D.Double point) {
		generalPath.lineTo((float)point.getX(), (float)point.getY());
	}

	public void arcTo(double radiusX, double radiusY, double angle, boolean largeArc, boolean sweep, Point2D.Double point) {
		generalPath.arcTo((float)radiusX, (float)radiusY, (float)angle, largeArc, sweep, (float)point.getX(), (float)point.getY());
	}

	public void relToAbs(Point2D.Double point) {
		point.setLocation(point.getX() + center.getX(), point.getY() + center.getY());
	}

	public int command(int index) {
		//TODO completare
		return 0;
	}

	public int getTotalVertices() {
		//TODO controllare
		return totalVerices;
	}

	public boolean isVertex(int index) {
		//TODO completare
		return false;
	}

	public void vertex(Point2D.Double point) {
		//TODO completare
	}

	public int lastVertex(Point2D.Double point) {
		//TODO completare
		return 0;
	}

	public void modifyVertex(int index, Point2D.Double point) {
		//TODO completare
	}

	public void transform(AffineTransform t, int index) {
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
