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
import java.util.Hashtable;

public class Assembly extends Solid {
	private final Hashtable<String, Solid> solids;

	public Assembly(final String name) {
		super(name);
		solids = new Hashtable<String, Solid>();
	}

	public final void removePart(final Part solid) {
		solids.remove(solid.getName());
	}

	public final void addPart(final Part solid) {
		if (solid.getName() != null) {
			solids.put(solid.getName(), solid);
		}
	}

	public final void removeAssembly(final Assembly solid) {
		solids.remove(solid.getName());
	}

	public final void addAssembly(final Assembly solid) {
		if (solid.getName() != null) {
			solids.put(solid.getName(), solid);
		}
	}

	public final Solid getSolid(final String name) {
		return (solids.get(name));
	}

	public final Enumeration<Solid> getSolids() {
		return (solids.elements());
	}

	@Override
	final void project(Matrix ml, Matrix mt, final float vx, final float vy, final float vz) {
		Solid solid = null;
		mt = Matrix.multiply(mt, mp);
		ml = Matrix.multiply(ml, mr);
		final Enumeration<Solid> solids = getSolids();
		while (solids.hasMoreElements()) {
			solid = solids.nextElement();
			solid.project(ml, mt, vx, vy, vz);
		}
	}

	@Override
	final void rebuild() {
		Solid solid = null;
		final Enumeration<Solid> solids = getSolids();
		while (solids.hasMoreElements()) {
			solid = solids.nextElement();
			solid.parent = this;
			solid.rebuild();
		}
	}

	@Override
	public final void transform(final Matrix mt) {
		Solid solid = null;
		final Enumeration<Solid> solids = getSolids();
		while (solids.hasMoreElements()) {
			solid = solids.nextElement();
			solid.transform(mt);
		}
	}

	@Override
	public String toString() {
		final StringBuffer s = new StringBuffer("assembly: " + name + "\r\n");
		s.append("parent: " + ((parent != null) ? parent.name : "unknow") + "\r\n");
		Solid solid = null;
		final Enumeration<Solid> solids = getSolids();
		while (solids.hasMoreElements()) {
			solid = solids.nextElement();
			s.append(solid.toString());
		}
		return (s.toString());
	}
}
