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

public final class Vertex {
	public int id = 0;
	Part parent;
	Vector q;
	Vector p;
	Vector np;
	Vector nq;
	float u = 0;
	float v = 0;
	int px = 0;
	int py = 0;
	int pz = 0;
	int tx = 0;
	int ty = 0;
	int nx = 0;
	int ny = 0;
	int nz = 0;
	int clipcode = 0;
	boolean visible = true;
	java.util.Vector<Triangle> neighbors = new java.util.Vector<Triangle>();

	public Vertex(final float x, final float y, final float z) {
		q = new Vector(x, y, z);
		p = new Vector(x, y, z);
	}

	public Vertex(final float x, final float y, final float z, final float u, final float v) {
		this(x, y, z);
		this.u = u;
		this.v = v;
	}

	public Vertex(final Vertex v) {
		this(v.q.x, v.q.y, v.q.z, v.u, v.v);
	}

	public Vertex(final Vector v) {
		this(v.x, v.y, v.z);
	}

	public int getClipCode() {
		return (Vertex.getClipCode(p));
	}

	public static int getClipCode(final Vector v) {
		int c = 0;
		if (v.x < -v.z) {
			c = 1;
		}
		else if (v.x > +v.z) {
			c = 2;
		}
		if (v.y < -v.z) {
			c = c | 4;
		}
		else if (v.y > +v.z) {
			c = c | 8;
		}
		if (v.z < 0.01f) {
			c = c | 16;
		}
		return (c);
	}

	void build() {
		float qx = 0;
		float qy = 0;
		float qz = 0;
		final Enumeration<Triangle> e = neighbors.elements();
		while (e.hasMoreElements()) {
			final Triangle t = e.nextElement();
			final Vector v = t.getWeightedNormal();
			qx += v.x;
			qy += v.y;
			qz += v.z;
		}
		nq = new Vector(qx, qy, qz).normalize();
		if ((parent.material != null) && (parent.material.texture != null)) {
			tx = (int) (parent.material.texture.width * u);
			ty = (int) (parent.material.texture.height * v);
		}
	}

	void project(final Matrix ml, final Matrix mt, final float vx, final float vy, final float vz) {
		p = q.transform(mt);
		np = nq.rotate(ml);
		nx = (int) ((np.x * 127f) + 127f);
		ny = (int) ((np.y * 127f) + 127f);
		nz = (int) (np.z * 65536f);
		final float qz = ((p.z < 0.1f) ? 0.1f : p.z) * vz;
		px = (int) ((p.x * vx) / qz);
		py = (int) ((p.y * vy) / qz);
		pz = (int) (p.z * 65536f);
		clipcode = Vertex.getClipCode(p);
	}

	void transform(final Matrix mt) {
		q = q.transform(mt);
		p = new Vector(q);
	}

	void registerNeighbor(final Triangle triangle) {
		if (!neighbors.contains(triangle)) {
			neighbors.addElement(triangle);
		}
	}

	void resetNeighbors() {
		neighbors.removeAllElements();
	}

	public boolean equals(final Vertex v) {
		return (q.equals(v.q));
	}

	public boolean equals(final Vertex v, final float tolerance) {
		return (q.equals(v.q, tolerance));
	}

	@Override
	public String toString() {
		return new String("<vertex: id = " + id + " x =" + p.x + " y =" + p.y + " z =" + p.z + ">");
	}
}
