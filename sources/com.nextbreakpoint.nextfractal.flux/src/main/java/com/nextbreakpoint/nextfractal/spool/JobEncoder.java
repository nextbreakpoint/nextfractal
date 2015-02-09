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

import com.nextbreakpoint.nextfractal.spool.store.StoreData;
import com.nextbreakpoint.nextfractal.spool.store.StoreService;

/**
 * @author Andrea Medeghini
 */
public class JobEncoder {
	private byte[] bytes;

	/**
	 * @param storeData
	 * @param jobDataRow
	 * @param jobData
	 * @throws Exception 
	 */
	public JobEncoder(StoreService<?> storeService, final StoreData storeData, final JobData jobDataRow, final byte[] jobData) throws Exception {
		if (jobDataRow == null) {
			throw new IllegalArgumentException("jobDataRow is null");
		}
		if (jobData == null) {
			throw new IllegalArgumentException("jobData is null");
		}
		if (storeData == null) {
			throw new IllegalArgumentException("data is null");
		}
		try {
			final ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			storeService.saveStoreData(baos2, storeData);
			baos2.close();
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			byte[] bytes = baos2.toByteArray();
			oos.writeObject(bytes);
			oos.writeObject(jobDataRow);
			oos.writeObject(jobData);
			oos.close();
			baos.close();
			bytes = baos.toByteArray();
		}
		catch (final Exception e) {
			throw new IOException("An error has happened marshalling the data: " + e.getMessage());
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
