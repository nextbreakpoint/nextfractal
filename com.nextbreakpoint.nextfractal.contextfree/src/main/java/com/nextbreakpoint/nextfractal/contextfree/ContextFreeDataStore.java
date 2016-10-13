/*
 * NextFractal 1.3.0
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
package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.utils.Block;

import java.io.*;

import static javax.xml.bind.JAXB.marshal;
import static javax.xml.bind.JAXB.unmarshal;

public class ContextFreeDataStore {
	public ContextFreeData loadFromFile(File path) throws Exception {
		return Try.of(() -> unmarshal(path, ContextFreeData.class))
				.mapper(e -> new Exception("Cannot load data from file " + path.getAbsolutePath())).orThrow();
	}

	public void saveToFile(File path, ContextFreeData data) throws Exception {
		Block.create(ContextFreeData.class).andThen(d -> marshal(d, path)).tryExecute(data)
				.mapper(e -> new Exception("Cannot save data to file " + path.getAbsolutePath())).orThrow();
	}

	public ContextFreeData loadFromReader(Reader reader) throws Exception {
		return Try.of(() -> unmarshal(reader, ContextFreeData.class))
				.mapper(e -> new Exception("Cannot load data from reader")).orThrow();
	}

	public void saveToWriter(Writer writer, ContextFreeData data) throws Exception {
		Block.create(ContextFreeData.class).andThen(d -> marshal(d, writer)).tryExecute(data)
				.mapper(e -> new Exception("Cannot save data to writer")).orThrow();
	}

	public ContextFreeData loadFromStream(InputStream stream) throws Exception {
		return Try.of(() -> unmarshal(stream, ContextFreeData.class))
				.mapper(e -> new Exception("Cannot load data from stream")).orThrow();
	}

	public void saveToStream(OutputStream stream, ContextFreeData data) throws Exception {
		Block.create(ContextFreeData.class).andThen(d -> marshal(d, stream)).tryExecute(data)
				.mapper(e -> new Exception("Cannot save data to stream")).orThrow();
	}
}
