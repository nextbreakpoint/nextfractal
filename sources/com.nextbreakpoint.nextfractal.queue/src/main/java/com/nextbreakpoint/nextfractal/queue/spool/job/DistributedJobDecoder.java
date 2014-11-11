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
package com.nextbreakpoint.nextfractal.queue.spool.job;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.w3c.dom.Document;

import com.nextbreakpoint.nextfractal.core.runtime.xml.XML;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class DistributedJobDecoder {
	private JobData jobDataRow;
	private TwisterClip clip;
	private byte[] frameData;

	/**
	 * @param data
	 * @throws Exception
	 */
	public DistributedJobDecoder(final byte[] data) throws Exception {
		try {
			final ByteArrayInputStream bais = new ByteArrayInputStream(data);
			final ObjectInputStream ois = new ObjectInputStream(bais);
			byte[] clipData = (byte[]) ois.readObject();
			final ByteArrayInputStream bais2 = new ByteArrayInputStream(clipData);
			jobDataRow = (JobData) ois.readObject();
			frameData = (byte[]) ois.readObject();
			clip = readClip(bais2);
			bais2.close();
			ois.close();
			bais.close();
		}
		catch (final Exception e) {
			throw new Exception("An error has happened unmarshalling the data: " + e.getMessage(), e);
		}
		if (jobDataRow == null) {
			throw new Exception("An error has happened unmarshalling the data: jobDataRow is null");
		}
		if (clip == null) {
			throw new Exception("An error has happened unmarshalling the data: clip is null");
		}
	}

	private TwisterClip readClip(final InputStream is) throws IOException {
		try {
			final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
			final Document doc = XML.loadDocument(is, "twister-clip.xml");
			return importer.importFromElement(doc.getDocumentElement());
		}
		catch (final Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * @return the clip
	 */
	public TwisterClip getClip() {
		return clip;
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
