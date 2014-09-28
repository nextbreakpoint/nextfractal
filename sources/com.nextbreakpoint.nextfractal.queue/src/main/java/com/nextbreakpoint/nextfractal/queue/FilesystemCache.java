/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.queue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public interface FilesystemCache {
	/**
	 * @return
	 */
	public File[] list();

	/**
	 * @param id
	 * @throws IOException
	 */
	public void create(final int id) throws IOException;

	/**
	 * @param id
	 * @throws IOException
	 */
	public void delete(final int id) throws IOException;

	/**
	 * @param id
	 * @return
	 */
	public boolean exists(final int id);

	/**
	 * 
	 */
	public void deleteAll();

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(final int id) throws IOException;

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream(final int id) throws IOException;

	/**
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getRandomAccessFile(final int id) throws IOException;
}
