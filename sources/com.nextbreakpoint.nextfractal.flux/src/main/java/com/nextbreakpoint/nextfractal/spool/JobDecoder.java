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

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import com.nextbreakpoint.nextfractal.spool.store.StoreData;
import com.nextbreakpoint.nextfractal.spool.store.StoreService;

/**
 * @author Andrea Medeghini
 */
public class JobDecoder {
	private JobData jobDataRow;
	private StoreData storeData;
	private byte[] frameData;

	/**
	 * @param encodedData
	 * @throws Exception
	 */
	public JobDecoder(StoreService<?> storeService, final byte[] encodedData) throws Exception {
		try {
			final ByteArrayInputStream bais = new ByteArrayInputStream(encodedData);
			final ObjectInputStream ois = new ObjectInputStream(bais);
			byte[] bytes = (byte[]) ois.readObject();
			final ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes);
			jobDataRow = (JobData) ois.readObject();
			frameData = (byte[]) ois.readObject();
			storeData = storeService.loadStoreData(bais2);
			bais2.close();
			ois.close();
			bais.close();
		}
		catch (final Exception e) {
			throw new Exception("An error has happened unmarshalling the storeData: " + e.getMessage(), e);
		}
		if (jobDataRow == null) {
			throw new Exception("An error has happened unmarshalling the storeData: jobDataRow is null");
		}
		if (encodedData == null) {
			throw new Exception("An error has happened unmarshalling the storeData: storeData is null");
		}
	}

	/**
	 * @return
	 */
	public StoreData getSpoolData() {
		return storeData;
	}

	/**
	 * @return the jobData
	 */
	public JobData getJobData() {
		return jobDataRow;
	}

	/**
	 * @return the frameData
	 */
	public byte[] getFrameData() {
		return frameData;
	}
}
