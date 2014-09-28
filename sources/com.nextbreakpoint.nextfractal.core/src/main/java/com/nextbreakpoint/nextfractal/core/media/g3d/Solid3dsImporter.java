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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Solid3dsImporter {
	private boolean endOfStream = false;
	private int nextJunkOffset = 0;
	private int currentJunkId = 0;
	private Assembly assembly;
	private String partName;
	private Part part;

	public Solid3dsImporter() {
	}

	public void importFromURL(final URL url, final Assembly assembly) throws IOException {
		importFromStream(url.openStream(), assembly);
	}

	public void importFromFile(final File file, final Assembly assembly) throws IOException {
		importFromStream(new FileInputStream(file), assembly);
	}

	public void importFromStream(final InputStream stream, final Assembly assembly) throws IOException {
		final BufferedInputStream in = new BufferedInputStream(stream);
		this.assembly = assembly;
		readJunkHeader(in);
		if (currentJunkId != 0x4D4D) {
			throw new IOException("this is no valid 3ds file!");
		}
		while (!endOfStream) {
			readNextJunk(in);
		}
	}

	private static String readString(final InputStream in) throws IOException {
		String result = new String();
		byte inByte;
		while ((inByte = (byte) in.read()) != 0) {
			result += (char) inByte;
		}
		return result;
	}

	private static int readInt(final InputStream in) throws IOException {
		return (in.read() | (in.read() << 8) | (in.read() << 16) | (in.read() << 24));
	}

	private static int readShort(final InputStream in) throws IOException {
		return (in.read() | (in.read() << 8));
	}

	private static float readFloat(final InputStream in) throws IOException {
		return Float.intBitsToFloat(Solid3dsImporter.readInt(in));
	}

	private void readJunkHeader(final InputStream in) throws IOException {
		currentJunkId = Solid3dsImporter.readShort(in);
		nextJunkOffset = Solid3dsImporter.readInt(in);
		endOfStream = currentJunkId < 0;
	}

	private void readNextJunk(final InputStream in) throws IOException {
		readJunkHeader(in);
		if (currentJunkId == 0x3D3D) {
			return; // Mesh block
		}
		if (currentJunkId == 0x4000) // Object block
		{
			partName = Solid3dsImporter.readString(in);
			return;
		}
		if (currentJunkId == 0x4100) // Solid object
		{
			part = new Part(partName);
			assembly.addPart(part);
			return;
		}
		if (currentJunkId == 0x4110) // Vertex list
		{
			readVertexList(in);
			return;
		}
		if (currentJunkId == 0x4120) // Point list
		{
			readPointList(in);
			return;
		}
		if (currentJunkId == 0x4140) // Mapping coordinates
		{
			readMappingCoordinates(in);
			return;
		}
		skipJunk(in);
	}

	private void skipJunk(final InputStream in) throws IOException, OutOfMemoryError {
		for (int i = 0; (i < nextJunkOffset - 6) && (!endOfStream); i++) {
			endOfStream = in.read() < 0;
		}
	}

	private void readVertexList(final InputStream in) throws IOException {
		float x, y, z;
		final int vertices = Solid3dsImporter.readShort(in);
		for (int i = 0; i < vertices; i++) {
			x = Solid3dsImporter.readFloat(in);
			y = Solid3dsImporter.readFloat(in);
			z = Solid3dsImporter.readFloat(in);
			part.addVertex(new Vertex(+x, -y, +z));
		}
	}

	private void readPointList(final InputStream in) throws IOException {
		int v1, v2, v3;
		final int triangles = Solid3dsImporter.readShort(in);
		for (int i = 0; i < triangles; i++) {
			v1 = Solid3dsImporter.readShort(in);
			v2 = Solid3dsImporter.readShort(in);
			v3 = Solid3dsImporter.readShort(in);
			Solid3dsImporter.readShort(in);
			part.addTriangle(new Triangle(true, part.vertex(v1), part.vertex(v2), part.vertex(v3)));
		}
	}

	private void readMappingCoordinates(final InputStream in) throws IOException {
		final int vertices = Solid3dsImporter.readShort(in);
		for (int i = 0; i < vertices; i++) {
			part.vertex(i).u = Solid3dsImporter.readFloat(in);
			part.vertex(i).v = Solid3dsImporter.readFloat(in);
		}
	}
}
