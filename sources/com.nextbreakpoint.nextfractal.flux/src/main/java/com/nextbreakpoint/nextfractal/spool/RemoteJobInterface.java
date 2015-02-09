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

import java.io.IOException;

import com.nextbreakpoint.nextfractal.core.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.spool.store.StoreData;

/**
 * @author Andrea Medeghini
 */
public interface RemoteJobInterface extends JobInterface {
	/**
	 * @param jobData
	 */
	public void setJobData(byte[] jobData);

	/**
	 * @return
	 */
	public byte[] getJobData();

	/**
	 * @param job
	 */
	@Override
	public void setJobDataRow(JobData job);

	/**
	 * @return
	 */
	@Override
	public JobData getJobDataRow();

	/**
	 * @return
	 * @throws IOException
	 */
	public StoreData getStoreData() throws IOException;

	/**
	 * @param data
	 * @throws IOException
	 */
	public void setStoreData(StoreData data) throws IOException;

	/**
	 * @param frameNumber
	 */
	@Override
	public void setFirstFrameNumber(int frameNumber);

	/**
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getRAF() throws IOException;
}
