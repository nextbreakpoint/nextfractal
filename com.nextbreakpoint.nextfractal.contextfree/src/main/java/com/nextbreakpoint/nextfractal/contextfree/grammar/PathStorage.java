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

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class PathStorage implements Cloneable {
	private ExtendedGeneralPath currentPath = new ExtendedGeneralPath();
	private List<Vertex> vertices = new ArrayList<>();

	public void startNewPath() {
		currentPath = new ExtendedGeneralPath();
		vertices.clear();
	}

	public void closePath() {
		currentPath.closePath();
	}

	public void endPath() {
		currentPath.closePath();
	}

	public void moveTo(Point2D.Double point) {
		vertices.add(new Vertex(point, 1));
		currentPath.moveTo((float)point.getX(), (float)point.getY());
	}

	public void lineTo(Point2D.Double point) {
		vertices.add(new Vertex(point, 2));
		currentPath.lineTo((float)point.getX(), (float)point.getY());
	}

	public void arcTo(double radiusX, double radiusY, double angle, boolean largeArc, boolean sweep, Point2D.Double point) {
		vertices.add(new Vertex(point, 3));
		currentPath.arcTo((float)radiusX, (float)radiusY, (float)angle, largeArc, sweep, (float)point.getX(), (float)point.getY());
	}

	public void relToAbs(Point2D.Double point) {
		//TODO controllare
//		point.setLocation(point.getX() + center.getX(), point.getY() + center.getY());
	}

	public int command(int index) {
		return vertices.get(index).command;
	}

	public int getTotalVertices() {
		return vertices.size();
	}

	public int vertex(int index, Point2D.Double point) {
		Vertex vertex = vertices.get(index);
		point.setLocation(vertex.point.getX(), vertex.point.getY());
		return vertex.command;
	}

	public int lastVertex(Point2D.Double point) {
		Vertex vertex = vertices.get(vertices.size() - 1);
		point.setLocation(vertex.point.getX(), vertex.point.getY());
		return vertex.command;
	}

	public void modifyVertex(int index, Point2D.Double point) {
		Vertex vertex = vertices.get(index);
		vertex.point.setLocation(point.getX(), point.getY());
	}

	public boolean isDrawing(int command) {
		return command >= 2 && command <= 3;
	}

	public boolean isVertex(int command) {
		return command >= 1 && command <= 3;
	}

	public boolean isCurve(int command) {
		return command == 3;
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
		return getGeneralPath().getPathIterator(new AffineTransform());
	}

	public ExtendedGeneralPath getGeneralPath() {
		return currentPath;
	}

	public void append(Shape shape, Point2D.Double point) {
		vertices.add(new Vertex(point, 3));
		currentPath.append(shape, true);
	}

	public int lastCommnand() {
		if (vertices.size() == 0) {
			return 0;
		}
		return vertices.get(vertices.size() - 1).command;
	}

	public Object clone() {
		PathStorage storage = new PathStorage();
		storage.currentPath = (ExtendedGeneralPath) currentPath.clone();
		return storage;
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
