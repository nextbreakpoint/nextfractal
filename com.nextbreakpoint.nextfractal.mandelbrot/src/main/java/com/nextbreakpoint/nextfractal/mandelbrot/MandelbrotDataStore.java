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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.utils.Block;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import static javax.xml.bind.JAXB.marshal;
import static javax.xml.bind.JAXB.unmarshal;

public class MandelbrotDataStore {
	public MandelbrotData loadFromFile(File path) throws Exception {
		return Try.of(() -> unmarshal(path, MandelbrotData.class))
				.or(() -> unmarshal(path, MandelbrotDataV10.class).toMandelbrotData())
				.mapper(e -> new Exception("Cannot load data from file " + path.getAbsolutePath())).orThrow();
	}

	public void saveToFile(File path, MandelbrotData data) throws Exception {
		Block.create(MandelbrotData.class).andThen(d -> marshal(d, path)).tryExecute(data)
				.mapper(e -> new Exception("Cannot save data to file " + path.getAbsolutePath())).orThrow();
	}

	public MandelbrotData loadFromReader(Reader reader) throws Exception {
		return Try.of(() -> unmarshal(reader, MandelbrotData.class))
				.or(() -> unmarshal(reader, MandelbrotDataV10.class).toMandelbrotData())
				.mapper(e -> new Exception("Cannot load data from reader")).orThrow();
	}

	public void saveToWriter(Writer writer, MandelbrotData data) throws Exception {
		Block.create(MandelbrotData.class).andThen(d -> marshal(d, writer)).tryExecute(data)
				.mapper(e -> new Exception("Cannot save data to writer")).orThrow();
	}

	public MandelbrotData loadFromStream(InputStream stream) throws Exception {
		return Try.of(() -> unmarshal(stream, MandelbrotData.class))
				.or(() -> unmarshal(stream, MandelbrotDataV10.class).toMandelbrotData())
				.mapper(e -> new Exception("Cannot load data from stream")).orThrow();
	}

	public void saveToStream(OutputStream stream, MandelbrotData data) throws Exception {
		Block.create(MandelbrotData.class).andThen(d -> marshal(d, stream)).tryExecute(data)
				.mapper(e -> new Exception("Cannot save data to stream")).orThrow();
	}
}
