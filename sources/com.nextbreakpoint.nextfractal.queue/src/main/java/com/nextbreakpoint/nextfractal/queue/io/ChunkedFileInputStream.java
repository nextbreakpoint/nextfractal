/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.queue.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Andrea Medeghini
 */
public class ChunkedFileInputStream extends InputStream {
	private final int chunkLength;
	private final String prefix;
	private final String suffix;
	private final File path;
	private int chunk;
	private long length;
	private FileInputStream is;

	/**
	 * @param path
	 * @param prefix
	 * @param suffix
	 * @param chunkLength
	 * @throws IOException
	 */
	public ChunkedFileInputStream(final File path, final String prefix, final String suffix, final int chunkLength) throws IOException {
		this.path = path;
		this.prefix = prefix;
		this.suffix = suffix;
		this.chunkLength = chunkLength;
	}

	/**
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		if (is == null) {
			is = new FileInputStream(new File(path, prefix + chunk + suffix));
		}
		int value = -1;
		if (is != null) {
			value = is.read();
			length += 1;
			if ((length % chunkLength) == 0) {
				chunk += 1;
				is.close();
				is = null;
			}
		}
		return value;
	}

	/**
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		if (is != null) {
			is.close();
			is = null;
		}
		super.close();
	}
}
