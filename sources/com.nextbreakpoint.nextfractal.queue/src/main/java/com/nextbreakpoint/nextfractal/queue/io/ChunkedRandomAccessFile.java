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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class ChunkedRandomAccessFile implements Closeable {
	private final int chunkLength;
	private final String prefix;
	private final String suffix;
	private final File path;
	private int chunk;
	private int offset;
	private long position;
	private RandomAccessFile raf;

	/**
	 * @param path
	 * @param prefix
	 * @param suffix
	 * @param chunkLength
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile(final File path, final String prefix, final String suffix, final int chunkLength) throws IOException {
		this.path = path;
		this.prefix = prefix;
		this.suffix = suffix;
		this.chunkLength = chunkLength;
	}

	private RandomAccessFile getRandomAccessFile() throws IOException {
		if (raf == null) {
			raf = new RandomAccessFile(new File(path, prefix + chunk + suffix), "rw");
			raf.seek(offset);
		}
		return raf;
	}

	private void setPosition(final long pos) throws IOException {
		long n = chunk * chunkLength;
		if ((pos < n) || (pos >= (n + chunkLength))) {
			if (raf != null) {
				raf.close();
				raf = null;
			}
		}
		offset = (int) (pos % chunkLength);
		chunk = (int) (pos / chunkLength);
		position = pos;
		if (raf != null) {
			raf.seek(offset);
		}
	}

	public void seek(final long pos) throws IOException {
		setPosition(pos);
	}

	public int read() throws IOException {
		RandomAccessFile raf = getRandomAccessFile();
		int value = raf.read();
		setPosition(position + 1);
		return value;
	}

	public void readFully(final byte[] b) throws IOException {
		int len = b.length;
		int m = len / chunkLength;
		int r = len % chunkLength;
		RandomAccessFile raf = getRandomAccessFile();
		int n = 0;
		if (m > 0) {
			n = chunkLength - offset;
			raf.read(b, 0, n);
			setPosition(position + n);
			for (int i = 1; i < m; i++) {
				raf = getRandomAccessFile();
				raf.read(b, n, chunkLength);
				n += chunkLength;
				setPosition(position + n);
			}
		}
		if (r > 0) {
			raf = getRandomAccessFile();
			raf.read(b, n, r);
			n += r;
			setPosition(position + n);
		}
	}

	public void readFully(final byte[] b, final int off, final int len) throws IOException {
		int m = len / chunkLength;
		int r = len % chunkLength;
		RandomAccessFile raf = getRandomAccessFile();
		int n = 0;
		if (m > 0) {
			n = chunkLength - offset;
			raf.read(b, off, n);
			setPosition(position + n);
			for (int i = 1; i < m; i++) {
				raf = getRandomAccessFile();
				raf.read(b, off + n, chunkLength);
				n += chunkLength;
				setPosition(position + n);
			}
		}
		if (r > 0) {
			raf = getRandomAccessFile();
			raf.read(b, off + n, r);
			n += r;
			setPosition(position + n);
		}
	}

	public void write(final int b) throws IOException {
		RandomAccessFile raf = getRandomAccessFile();
		raf.write(b);
		setPosition(position + 1);
	}

	public void write(final byte[] b) throws IOException {
		int len = b.length;
		int m = len / chunkLength;
		int r = len % chunkLength;
		RandomAccessFile raf = getRandomAccessFile();
		int n = 0;
		if (m > 0) {
			n = chunkLength - offset;
			raf.write(b, 0, n);
			setPosition(position + n);
			for (int i = 1; i < m; i++) {
				raf = getRandomAccessFile();
				raf.write(b, n, chunkLength);
				n += chunkLength;
				setPosition(position + n);
			}
		}
		if (r > 0) {
			raf = getRandomAccessFile();
			raf.write(b, n, r);
			n += r;
			setPosition(position + n);
		}
	}

	public void write(final byte[] b, final int off, final int len) throws IOException {
		int m = len / chunkLength;
		int r = len % chunkLength;
		RandomAccessFile raf = getRandomAccessFile();
		int n = 0;
		if (m > 0) {
			n = chunkLength - offset;
			raf.write(b, off, n);
			setPosition(position + n);
			for (int i = 1; i < m; i++) {
				raf = getRandomAccessFile();
				raf.write(b, off + n, chunkLength);
				n += chunkLength;
				setPosition(position + n);
			}
		}
		if (r > 0) {
			raf = getRandomAccessFile();
			raf.write(b, off + n, r);
			n += r;
			setPosition(position + n);
		}
	}

	/**
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if (raf != null) {
			raf.close();
			raf = null;
		}
	}
}
