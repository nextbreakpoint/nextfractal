/*
 * NextFractal 1.1.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.bind.JAXB;

public class MandelbrotDataStore {
	public MandelbrotData loadFromFile(File path) throws Exception {
		try {
			return JAXB.unmarshal(path, MandelbrotData.class);
		} catch (Exception e) {
			throw new Exception("Cannot load data from file " + path.getAbsolutePath());
		}
	}

	public void saveToFile(File path, MandelbrotData data) throws Exception {
		try {
			JAXB.marshal(data, path);
		} catch (Exception e) {
			throw new Exception("Cannot save data to file " + path.getAbsolutePath());
		}
	}

	public MandelbrotData loadFromReader(Reader reader) throws Exception {
		try {
			return JAXB.unmarshal(reader, MandelbrotData.class);
		} catch (Exception e) {
			throw new Exception("Cannot load data from reader");
		}
	}

	public void saveToWriter(Writer writer, MandelbrotData data) throws Exception {
		try {
			JAXB.marshal(data, writer);
		} catch (Exception e) {
			throw new Exception("Cannot save data to writer");
		}
	}

	public MandelbrotData loadFromStream(InputStream is) throws Exception {
		try {
			return JAXB.unmarshal(is, MandelbrotData.class);
		} catch (Exception e) {
			throw new Exception("Cannot load data from stream");
		}
	}

	public void saveToStream(OutputStream os, MandelbrotData data) throws Exception {
		try {
			JAXB.marshal(data, os);
		} catch (Exception e) {
			throw new Exception("Cannot save data to stream");
		}
	}
}
