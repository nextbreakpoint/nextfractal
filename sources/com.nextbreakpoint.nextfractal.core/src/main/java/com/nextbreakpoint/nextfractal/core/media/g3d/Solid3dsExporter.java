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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class Solid3dsExporter {
	private Solid3dsExporter() {
	}

	public static void exportToStream(final OutputStream stream, final Assembly assembly) throws IOException {
		final BufferedOutputStream out = new BufferedOutputStream(stream);
		Solid3dsExporter.exportContainer(assembly, out);
		stream.close();
	}

	private static void writeString(final String string, final OutputStream out) throws IOException {
		out.write(string.getBytes("US-ASCII"));
		out.write(0);
	}

	private static void writeInt(final int outInt, final OutputStream out) throws IOException {
		out.write((outInt >> 0) & 255);
		out.write((outInt >> 8) & 255);
		out.write((outInt >> 16) & 255);
		out.write((outInt >> 24) & 255);
	}

	private static void writeShort(final int outShort, final OutputStream out) throws IOException {
		out.write((outShort >> 0) & 255);
		out.write((outShort >> 8) & 255);
	}

	private static void writeFloat(final float outFloat, final OutputStream out) throws IOException {
		Solid3dsExporter.writeInt(Float.floatToIntBits(outFloat), out);
	}

	private static void exportContainer(final Assembly assembly, final OutputStream out) throws IOException {
		int runlength = 0;
		Part solid = null;
		final java.util.Vector<Solid> solids = new java.util.Vector<Solid>();
		Solid3dsExporter.enqueueSolids(assembly, solids);
		for (int i = 0; i < solids.size(); i++) {
			solid = (Part) solids.elementAt(i);
			runlength += solid.name.length() + 1;
			runlength += 36 + 20 * solid.vertices.length + 8 * solid.triangles.length;
		}
		Solid3dsExporter.writeShort(0x4D4D, out);
		Solid3dsExporter.writeInt(12 + runlength, out);
		Solid3dsExporter.writeShort(0x3D3D, out);
		Solid3dsExporter.writeInt(6 + runlength, out);
		for (int i = 0; i < solids.size(); i++) {
			solid = (Part) solids.elementAt(i);
			Solid3dsExporter.exportObject(solid, out);
		}
	}

	private static void enqueueSolids(final Assembly assembly, final java.util.Vector<Solid> solids) {
		Solid object = null;
		final Enumeration<Solid> objects = assembly.getSolids();
		while (objects.hasMoreElements()) {
			object = objects.nextElement();
			if (object instanceof Assembly) {
				Solid3dsExporter.enqueueSolids((Assembly) object, solids);
			}
			else if (object instanceof Part) {
				solids.addElement(object);
			}
		}
	}

	private static void exportObject(final Part solid, final OutputStream out) throws IOException {
		final int vJunkSize = 2 + 12 * solid.vertices();
		final int tJunkSize = 2 + 8 * solid.triangles();
		final int mcJunkSize = 2 + 8 * solid.vertices();
		Solid3dsExporter.writeShort(0x4000, out);
		Solid3dsExporter.writeInt(30 + vJunkSize + tJunkSize + mcJunkSize + solid.name.length() + 1, out);
		Solid3dsExporter.writeString(solid.name, out);
		Solid3dsExporter.writeShort(0x4100, out);
		Solid3dsExporter.writeInt(24 + vJunkSize + tJunkSize + mcJunkSize, out);
		Solid3dsExporter.writeShort(0x4110, out);
		Solid3dsExporter.writeInt(6 + vJunkSize, out);
		Solid3dsExporter.writeShort(solid.vertices(), out);
		for (int i = 0; i < solid.vertices(); i++) {
			Solid3dsExporter.exportVertex(solid.vertex(i), out);
		}
		Solid3dsExporter.writeShort(0x4120, out);
		Solid3dsExporter.writeInt(6 + tJunkSize, out);
		Solid3dsExporter.writeShort(solid.triangles(), out);
		for (int i = 0; i < solid.triangles(); i++) {
			Solid3dsExporter.exportTriangle(solid.triangle(i), out);
		}
		Solid3dsExporter.writeShort(0x4140, out);
		Solid3dsExporter.writeInt(6 + mcJunkSize, out);
		Solid3dsExporter.writeShort(solid.vertices(), out);
		for (int i = 0; i < solid.vertices(); i++) {
			Solid3dsExporter.exportMappingCoordinates(solid.vertex(i), out);
		}
	}

	private static void exportVertex(final Vertex v, final OutputStream out) throws IOException {
		Solid3dsExporter.writeFloat(+v.p.x, out);
		Solid3dsExporter.writeFloat(-v.p.y, out);
		Solid3dsExporter.writeFloat(+v.p.z, out);
	}

	private static void exportTriangle(final Triangle t, final OutputStream out) throws IOException {
		Solid3dsExporter.writeShort(t.p1.id, out);
		Solid3dsExporter.writeShort(t.p2.id, out);
		Solid3dsExporter.writeShort(t.p3.id, out);
		Solid3dsExporter.writeShort(0, out);
	}

	private static void exportMappingCoordinates(final Vertex v, final OutputStream out) throws IOException {
		Solid3dsExporter.writeFloat(v.u, out);
		Solid3dsExporter.writeFloat(v.v, out);
	}
}
