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
package com.nextbreakpoint.nextfractal.queue.clip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.nextbreakpoint.nextfractal.core.util.ZIPFilenameFilter;
import com.nextbreakpoint.nextfractal.queue.DefaultFilesystemCache;
import com.nextbreakpoint.nextfractal.queue.FilesystemCache;
import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class RenderClipFilesystemCache implements FilesystemCache {
	protected final File workdir;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public RenderClipFilesystemCache(final File workdir) throws IOException {
		this.workdir = workdir;
		workdir.mkdirs();
	}

	/**
	 * @return
	 */
	@Override
	public File[] list() {
		return workdir.listFiles(createFilenameFilter());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#create(int)
	 */
	@Override
	public void create(final int id) throws IOException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(getFile(id));
		}
		finally {
			if (os != null) {
				try {
					os.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#delete(int)
	 */
	@Override
	public void delete(final int id) throws IOException {
		final File file = getFile(id);
		if (file.exists()) {
			file.delete();
			if (file.exists()) {
				throw new IOException("Can't delete the file: " + file.getName());
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#deleteAll()
	 */
	@Override
	public void deleteAll() {
		final File[] files = workdir.listFiles();
		if (files != null) {
			for (final File file : files) {
				if (file.isDirectory()) {
					DefaultFilesystemCache.deleteDir(file);
				}
				else if (file.isFile()) {
					file.delete();
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#exists(int)
	 */
	@Override
	public boolean exists(final int id) {
		final File dir = getFile(id);
		return dir.exists();
	}

	/**
	 * @param id
	 * @return
	 */
	protected File getFile(final int id) {
		return new File(workdir, String.valueOf(id) + ".zip");
	}

	/**
	 * @return
	 */
	protected FilenameFilter createFilenameFilter() {
		return new ZIPFilenameFilter();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#getInputStream(int)
	 */
	@Override
	public InputStream getInputStream(final int id) throws IOException {
		return new FileInputStream(getFile(id));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#getOutputStream(int)
	 */
	@Override
	public OutputStream getOutputStream(final int id) throws IOException {
		return new FileOutputStream(getFile(id));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.FilesystemCache#getRandomAccessFile(int)
	 */
	@Override
	public ChunkedRandomAccessFile getRandomAccessFile(final int id) throws IOException {
		throw new UnsupportedOperationException();
	}
}
