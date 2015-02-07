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
import java.io.OutputStream;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.service.FileService;

/**
 * @author Andrea Medeghini
 */
public class DistributedJobEncoder {
	private final JobData jobData;
	private final SpoolData clip;
	private final byte[] frameData;

	/**
	 * @param clip
	 * @param jobDataRow
	 * @param jobData
	 */
	public DistributedJobEncoder(final SpoolData clip, final JobData jobDataRow, final byte[] jobData) {
		this.clip = clip;
		this.jobData = jobDataRow;
		frameData = jobData;
		if (jobDataRow == null) {
			throw new IllegalArgumentException("jobDataRow is null");
		}
		if (clip == null) {
			throw new IllegalArgumentException("clip is null");
		}
	}

	/**
	 * @return the clip
	 */
	public SpoolData getClip() {
		return clip;
	}

	/**
	 * @return the jobData
	 */
	public JobData getJobData() {
		return jobData;
	}

	/**
	 * @return the frameData
	 */
	public byte[] getFrameData() {
		return frameData;
	}

	private void writeClip(final OutputStream os, final SpoolData clip) throws IOException {
		try {
			FileService service = new FileService();
			MandelbrotData data = clip.getData();
			service.saveToStream(os, data);
		}
		catch (final Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytes() throws IOException {
		try {
			final ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			writeClip(baos2, clip);
			baos2.close();
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			byte[] clipData = baos2.toByteArray();
			oos.writeObject(clipData);
			oos.writeObject(jobData);
			oos.writeObject(frameData);
			oos.close();
			baos.close();
			final byte[] data = baos.toByteArray();
			return data;
		}
		catch (final Exception e) {
			throw new IOException("An error has happened marshalling the data: " + e.getMessage());
		}
	}
}
