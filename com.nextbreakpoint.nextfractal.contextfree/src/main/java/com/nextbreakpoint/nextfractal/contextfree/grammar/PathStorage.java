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

import java.util.ArrayList;
import java.util.List;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class PathStorage {
	private ExtendedGeneralPath generalPath = new ExtendedGeneralPath();
	private List<Vertex> vertices = new ArrayList<>();
	private Point2D.Double center = new Point2D.Double(0, 0);
	private boolean drawing = false;

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		//TODO controllare
		this.drawing = drawing;
	}

	public void clear() {
		generalPath.reset();
	}

	public void startNewPath() {
		generalPath = new ExtendedGeneralPath();
		vertices.clear();
	}

	public void closePolygon() {
		//TODO controllare
		generalPath.closePath();
	}

	public void moveTo(Point2D.Double point) {
		//TODO controllare
		vertices.add(new Vertex(point, 1));
		center.setLocation(point.getX(), point.getY());
		generalPath.moveTo((float)point.getX(), (float)point.getY());
	}

	public void lineTo(Point2D.Double point) {
		//TODO controllare
		vertices.add(new Vertex(point, 2));
		generalPath.lineTo((float)point.getX(), (float)point.getY());
	}

	public void arcTo(double radiusX, double radiusY, double angle, boolean largeArc, boolean sweep, Point2D.Double point) {
		//TODO controllare
		vertices.add(new Vertex(point, 3));
		generalPath.arcTo((float)radiusX, (float)radiusY, (float)angle, largeArc, sweep, (float)point.getX(), (float)point.getY());
	}

	public void relToAbs(Point2D.Double point) {
		//TODO controllare
		point.setLocation(point.getX() + center.getX(), point.getY() + center.getY());
	}

	public int command(int index) {
		return vertices.get(index).command;
	}

	public int getTotalVertices() {
		return vertices.size();
	}

	public boolean isVertex(int index) {
		//TODO controllare
		return vertices.get(index).command == 2;
	}

	public void addVertex(Point2D.Double point) {
		lineTo(point);
	}

	public int vertex(int index, Point2D.Double point) {
		Vertex vertex = vertices.get(index);
		point.setLocation(vertex.point.getX(), vertex.point.getY());
		return vertex.command;
	}

	public int lastVertex(Point2D.Double point) {
		//TODO controllare
		Vertex vertex = vertices.get(vertices.size() - 1);
		point.setLocation(vertex.point.getX(), vertex.point.getY());
		return vertex.command;
	}

	public void modifyVertex(int index, Point2D.Double point) {
		//TODO controllare
		Vertex vertex = vertices.get(vertices.size() - 1);
		vertex.point.setLocation(point.getX(), point.getY());
	}

	public void transform(AffineTransform t, int index) {
		//TODO completare
	}

	public boolean isCurve(int index) {
		return vertices.get(index).command == 3;
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

	private class Vertex {
		Point2D point;
		int command;

		public Vertex(Point2D.Double point, int command) {
			this.point = point;
			this.command = command;
		}
	}
}
