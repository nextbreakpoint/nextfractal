/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is based on code from idx3dIII
 * Copyright 1999, 2000 Peter Walser
 * http://www.idx3d.ch/idx3d/idx3d.html
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
package com.nextbreakpoint.nextfractal.core.media.g3d;

import java.util.Enumeration;

public class Part extends Solid {
	java.util.Vector<Triangle> triangles_vector = new java.util.Vector<Triangle>();
	java.util.Vector<Vertex> vertices_vector = new java.util.Vector<Vertex>();
	Triangle[] triangles;
	Vertex[] vertices;
	Material material;
	private Projector projector;
	private boolean dirty = false;
	public int id;

	public Part(final String name) {
		super(name);
		projector = new DefaultProjector();
		material = new DefaultMaterial();
	}

	public Part(final String name, final Material material, final Vertex[] vertices, final Triangle[] triangles, final Projector projector) {
		super(name);
		this.projector = projector;
		this.material = material;
		this.vertices = vertices;
		this.triangles = triangles;
		for (final Vertex element : vertices) {
			this.addVertex(element);
		}
		for (final Triangle element : triangles) {
			this.addTriangle(element);
		}
	}

	public final void addVertex(final Vertex v) {
		if ((v != null) && (!vertices_vector.contains(v))) {
			vertices_vector.addElement(v);
		}
		dirty = false;
	}

	public final void removeVertex(final Vertex v) {
		vertices_vector.removeElement(v);
		dirty = false;
	}

	public final void addTriangle(final Triangle t) {
		if ((t != null) && (!triangles_vector.contains(t))) {
			this.addVertex(t.p1);
			this.addVertex(t.p2);
			this.addVertex(t.p3);
			triangles_vector.addElement(t);
		}
		dirty = false;
	}

	public final void removeTriangle(final Triangle t) {
		triangles_vector.removeElement(t);
		dirty = false;
	}

	public final Triangle triangle(final int i) {
		return (triangles_vector.elementAt(i));
	}

	public final Vertex vertex(final int i) {
		return (vertices_vector.elementAt(i));
	}

	public final int triangles() {
		return (triangles_vector.size());
	}

	public final int vertices() {
		return (vertices_vector.size());
	}

	public final int indexOf(final Triangle t) {
		return (triangles_vector.indexOf(t));
	}

	public final int indexOf(final Vertex v) {
		return (vertices_vector.indexOf(v));
	}

	public final void addVertex(final float x, final float y, final float z, final float u, final float v) {
		final Vertex q = new Vertex(x, y, z, u, v);
		this.addVertex(q);
	}

	public final void addTriangle(final int v1, final int v2, final int v3) {
		this.addTriangle(vertex(v1), vertex(v2), vertex(v3));
	}

	public final void addTriangle(final boolean single, final int v1, final int v2, final int v3) {
		this.addTriangle(single, vertex(v1), vertex(v2), vertex(v3));
	}

	public final void addTriangle(final Vertex a, final Vertex b, final Vertex c) {
		this.addTriangle(new Triangle(a, b, c));
	}

	public final void addTriangle(final boolean single, final Vertex a, final Vertex b, final Vertex c) {
		this.addTriangle(new Triangle(single, a, b, c));
	}

	public final Vector min() {
		if (vertices.length == 0) {
			return (new Vector(0, 0, 0));
		}
		float minx = vertices[0].p.x;
		float miny = vertices[0].p.y;
		float minz = vertices[0].p.z;
		for (int i = 1; i < vertices.length; i++) {
			if (vertices[i].p.x < minx) {
				minx = vertices[i].p.x;
			}
			if (vertices[i].p.y < miny) {
				miny = vertices[i].p.y;
			}
			if (vertices[i].p.z < minz) {
				minz = vertices[i].p.z;
			}
		}
		return (new Vector(minx, miny, minz));
	}

	public final Vector max() {
		if (vertices.length == 0) {
			return (new Vector(0, 0, 0));
		}
		float maxx = vertices[0].p.x;
		float maxy = vertices[0].p.y;
		float maxz = vertices[0].p.z;
		for (int i = 1; i < vertices.length; i++) {
			if (vertices[i].p.x > maxx) {
				maxx = vertices[i].p.x;
			}
			if (vertices[i].p.y > maxy) {
				maxy = vertices[i].p.y;
			}
			if (vertices[i].p.z > maxz) {
				maxz = vertices[i].p.z;
			}
		}
		return (new Vector(maxx, maxy, maxz));
	}

	public final Vector getDimension() {
		final Vector min = min();
		final Vector max = max();
		return (new Vector(max.x - min.x, max.y - min.y, max.z - min.z));
	}

	public final Vector getCenter() {
		final Vector min = min();
		final Vector max = max();
		return (new Vector((max.x + min.x) / 2, (max.y + min.y) / 2, (max.z + min.z) / 2));
	}

	public final void inverti() {
		if (!dirty) {
			for (int i = 0; i < triangles(); i++) {
				triangle(i).inverti();
			}
		}
	}

	@Override
	final void project(Matrix ml, Matrix mt, final float vx, final float vy, final float vz) {
		mt = Matrix.multiply(mt, mp);
		ml = Matrix.multiply(ml, mr);
		for (final Vertex element : vertices) {
			element.project(ml, mt, vx, vy, vz);
		}
		for (final Triangle element : triangles) {
			element.project(ml, mt, vx, vy, vz);
		}
	}

	@Override
	final void rebuild() {
		if (!dirty) {
			vertices = new Vertex[vertices_vector.size()];
			for (int i = 0; i < vertices.length; i++) {
				vertices[i] = vertices_vector.elementAt(i);
			}
			triangles = new Triangle[triangles_vector.size()];
			for (int i = 0; i < triangles.length; i++) {
				triangles[i] = triangles_vector.elementAt(i);
			}
			if (projector != null) {
				projector.project(this);
			}
			for (final Vertex element : vertices) {
				element.parent = this;
				element.build();
			}
			for (final Triangle element : triangles) {
				element.parent = this;
				element.build();
			}
			dirty = true;
		}
	}

	@Override
	public final void transform(final Matrix mt) {
		if (!dirty) {
			for (int i = 0; i < vertices(); i++) {
				vertex(i).transform(mt);
			}
		}
	}

	public final void detach() {
		if (dirty) {
			final Vector center = getCenter();
			for (final Vertex element : vertices) {
				element.q.x -= center.x;
				element.q.y -= center.y;
				element.q.z -= center.z;
			}
			shift(center.x, center.y, center.z);
		}
	}

	@Override
	public String toString() {
		final StringBuffer s = new StringBuffer("part: " + name + "\r\n");
		s.append("parent: " + ((parent != null) ? parent.name : "unknow") + "\r\n");
		for (int i = 0; i < triangles(); i++) {
			s.append(triangle(i).toString());
		}
		return (s.toString());
	}

	public final void initid(final int id) {
		this.id = id;
		for (int i = 0; i < triangles(); i++) {
			triangle(i).id = (id << 24) | (i & 0xFFFFFF);
		}
	}

	public final void setMaterial(final Material material) {
		this.material = material;
	}

	public final void setProjector(final Projector projector) {
		if (!dirty) {
			this.projector = projector;
		}
	}

	public final void removeDuplicatedVertices() {
		final java.util.Vector<Edge> edgesToCollapse = new java.util.Vector<Edge>();
		for (int i = 0; i < vertices(); i++) {
			for (int j = i + 1; j < vertices(); j++) {
				if (vertex(i).equals(vertex(j), 0.0001f)) {
					edgesToCollapse.addElement(new Edge(vertex(i), vertex(j)));
				}
			}
		}
		final Enumeration<Edge> edges = edgesToCollapse.elements();
		while (edges.hasMoreElements()) {
			edgeCollapse(edges.nextElement());
		}
		removeDegeneratedTriangles();
	}

	public final void removeDegeneratedTriangles() {
		for (int i = 0; i < triangles(); i++) {
			if (triangle(i).degenerated()) {
				removeTriangle(triangle(i));
			}
		}
		dirty = false;
	}

	private void edgeCollapse(final Edge edge) {
		final Vertex a = edge.start();
		final Vertex b = edge.end();
		Triangle t;
		for (int i = 0; i < triangles(); i++) {
			t = triangle(i);
			if (t.p1 == b) {
				t.p1 = a;
			}
			if (t.p2 == b) {
				t.p2 = a;
			}
			if (t.p3 == b) {
				t.p3 = a;
			}
		}
		removeVertex(b);
	}

	public final Part copy() {
		final Part s = new Part(name + " copy");
		Triangle t;
		for (int i = 0; i < vertices(); i++) {
			s.addVertex(new Vertex(vertex(i)));
		}
		for (int i = 0; i < triangles(); i++) {
			t = triangle(i);
			final int p1 = this.indexOf(t.p1);
			final int p2 = this.indexOf(t.p2);
			final int p3 = this.indexOf(t.p3);
			s.addTriangle(t.single, p1, p2, p3);
		}
		return s;
	}

	public final void join(final Part s) {
		final int vertices = vertices();
		Triangle t;
		for (int i = 0; i < s.vertices(); i++) {
			this.addVertex(new Vertex(s.vertex(i)));
		}
		for (int i = 0; i < s.triangles(); i++) {
			t = s.triangle(i);
			final int p1 = s.indexOf(t.p1) + vertices;
			final int p2 = s.indexOf(t.p2) + vertices;
			final int p3 = s.indexOf(t.p3) + vertices;
			this.addTriangle(t.single, p1, p2, p3);
		}
	}
}
