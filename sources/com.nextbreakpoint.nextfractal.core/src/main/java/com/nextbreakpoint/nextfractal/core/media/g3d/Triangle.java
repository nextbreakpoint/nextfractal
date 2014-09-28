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

public final class Triangle {
	public int id = 0;
	Part parent;
	Vertex p1;
	Vertex p2;
	Vertex p3;
	Vector np;
	Vector nq;
	Vector c;
	int nx = 0;
	int ny = 0;
	int nz = 0;
	float IX = 0;
	float IY = 0;
	float IZ = 0;
	float JX = 0;
	float JY = 0;
	float JZ = 0;
	float OX = 0;
	float OY = 0;
	float OZ = 0;
	float HA = 0;
	float VA = 0;
	float OA = 0;
	float HB = 0;
	float VB = 0;
	float OB = 0;
	float HC = 0;
	float VC = 0;
	float OC = 0;
	float cosine = 0;
	float distance = 0;
	boolean single = false;
	boolean visible = true;

	public Triangle(final Vertex p1, final Vertex p2, final Vertex p3) {
		this(false, p1, p2, p3);
	}

	public Triangle(final boolean single, final Vertex p1, final Vertex p2, final Vertex p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.single = single;
		p1.registerNeighbor(this);
		p2.registerNeighbor(this);
		p3.registerNeighbor(this);
	}

	@Override
	public String toString() {
		final StringBuffer s = new StringBuffer("triangle: id = " + id + "\r\n");
		s.append(p1.p.toString() + "\r\n");
		s.append(p2.p.toString() + "\r\n");
		s.append(p3.p.toString() + "\r\n");
		return (s.toString());
	}

	void inverti() {
		final Vertex tv = p2;
		p2 = p3;
		p3 = tv;
	}

	void build() {
		nq = Vector.normal(p1.q, p2.q, p3.q);
		c = getCenter();
	}

	Vector getWeightedNormal() {
		return (Vector.weightedNormal(p1.q, p2.q, p3.q));
	}

	Vector getNormal() {
		return (Vector.normal(p1.q, p2.q, p3.q));
	}

	void project(final Matrix ml, final Matrix mt, final float vx, final float vy, final float vz) {
		final int c1 = p1.clipcode;
		final int c2 = p2.clipcode;
		final int c3 = p3.clipcode;
		boolean clipped = false;
		if ((c1 != 0) || (c2 != 0) || (c3 != 0)) {
			if (((c1 & 1) == 1) && ((c2 & 1) == 1) && ((c3 & 1) == 1)) {
				clipped = true;
			}
			else if (((c1 & 2) == 2) && ((c2 & 2) == 2) && ((c3 & 2) == 2)) {
				clipped = true;
			}
			else if (((c1 & 4) == 4) && ((c2 & 4) == 4) && ((c3 & 4) == 4)) {
				clipped = true;
			}
			else if (((c1 & 8) == 8) && ((c2 & 8) == 8) && ((c3 & 8) == 8)) {
				clipped = true;
			}
			else if (((c1 & 16) == 16) && ((c2 & 16) == 16) && ((c3 & 16) == 16)) {
				clipped = true;
			}
		}
		cosine = Vector.cos(c.transform(mt), nq.rotate(mt));
		if (single) {
			visible = (cosine > 0);
		}
		else {
			visible = true;
		}
		visible = (!clipped) && visible;
		if (visible) {
			np = nq.rotate(ml);
			nx = (int) ((np.x * 127f) + 127f);
			ny = (int) ((np.y * 127f) + 127f);
			nz = (int) (np.z * 65536f);
			distance = p1.p.z + p2.p.z + p3.p.z;
			IX = (p2.p.x - p1.p.x) * vx;
			IY = (p2.p.y - p1.p.y) * vy;
			IZ = (p2.p.z - p1.p.z) * vz;
			JX = (p3.p.x - p1.p.x) * vx;
			JY = (p3.p.y - p1.p.y) * vy;
			JZ = (p3.p.z - p1.p.z) * vz;
			OX = p1.p.x * vx;
			OY = p1.p.y * vy;
			OZ = p1.p.z * vz;
			HA = (JZ * OY) - (JY * OZ);
			VA = (JX * OZ) - (JZ * OX);
			OA = (JY * OX) - (JX * OY);
			HB = (IY * OZ) - (IZ * OY);
			VB = (IZ * OX) - (IX * OZ);
			OB = (IX * OY) - (IY * OX);
			HC = (IZ * JY) - (IY * JZ);
			VC = (IX * JZ) - (IZ * JX);
			OC = (IY * JX) - (IX * JY);
		}
	}

	public Vector getCenter() {
		final float cx = (p1.p.x + p2.p.x + p3.p.x) / 3f;
		final float cy = (p1.p.y + p2.p.y + p3.p.y) / 3f;
		final float cz = (p1.p.z + p2.p.z + p3.p.z) / 3f;
		return (new Vector(cx, cy, cz));
	}

	public boolean degenerated() {
		return (p1.equals(p2) || p2.equals(p3) || p3.equals(p1));
	}
}
