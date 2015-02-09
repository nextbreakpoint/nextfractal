package com.nextbreakpoint.nextfractal.spool;

import java.io.InputStream;
import java.io.OutputStream;

import com.nextbreakpoint.nextfractal.core.ChunkedRandomAccessFile;

public abstract class StoreService<T extends StoreData> {

	public InputStream getClipInputStream(int clipId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ChunkedRandomAccessFile getJobRandomAccessFile(int jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ChunkedRandomAccessFile getProfileRandomAccessFile(int profileId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWorkspace() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param is
	 * @return
	 */
	public abstract StoreData getSpoolData(InputStream is);

	/**
	 * @param os
	 * @param data
	 */
	public abstract void saveToStream(OutputStream os, StoreData data);
}
