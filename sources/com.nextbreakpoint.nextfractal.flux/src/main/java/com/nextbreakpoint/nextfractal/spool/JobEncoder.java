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
package com.nextbreakpoint.nextfractal.spool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Andrea Medeghini
 */
public class JobEncoder {
	private byte[] bytes;

	/**
	 * @param profile
	 * @param frameData
	 * @throws Exception 
	 */
	public JobEncoder(final JobProfile profile, final byte[] frameData) throws Exception {
		if (profile == null) {
			throw new IllegalArgumentException("profile is null");
		}
		if (frameData == null) {
			throw new IllegalArgumentException("frameData is null");
		}
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(profile);
			oos.writeObject(frameData);
			bytes = baos.toByteArray();
		}
		catch (final Exception e) {
			throw new IOException("An error has happened marshalling the data: " + e.getMessage());
		} finally {
			if (oos != null) {
				oos.close();
			}
			if (baos != null) {
				baos.close();
			}
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytes() throws IOException {
		return bytes;
	}
}
