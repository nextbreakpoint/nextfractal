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
package com.nextbreakpoint.nextfractal.queue.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.nextbreakpoint.nextfractal.queue.DataTableException;
import com.nextbreakpoint.nextfractal.queue.DefaultFilesystemCache;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.queue.SessionException;
import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;

/**
 * @author Andrea Medeghini
 */
public class RenderJobService {
	private final RenderJobFilesystemCache fileCache;
	private final RenderJobDataTable dataTable;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public RenderJobService(final File workdir) throws IOException {
		fileCache = new RenderJobFilesystemCache(new File(workdir, "job"));
		dataTable = new RenderJobDataTable();
	}

	/**
	 * @param session
	 * @param job
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void create(final Session session, final RenderJobDataRow job) throws SessionException, DataTableException {
		dataTable.create(session, job);
	}

	/**
	 * @param session
	 * @param job
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void delete(final Session session, final RenderJobDataRow job) throws SessionException, DataTableException {
		dataTable.delete(session, job);
		try {
			fileCache.delete(job.getJobId());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param session
	 * @param job
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void save(final Session session, final RenderJobDataRow job) throws SessionException, DataTableException {
		dataTable.save(session, job);
	}

	/**
	 * @param session
	 * @param id
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public boolean validate(final Session session, final int id) throws SessionException, DataTableException {
		final RenderJobDataRow job = dataTable.load(session, id);
		return fileCache.exists(job.getJobId());
	}

	/**
	 * @param session
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public List<RenderJobDataRow> loadAll(final Session session) throws SessionException, DataTableException {
		return dataTable.loadAll(session);
	}

	/**
	 * @param session
	 * @param profileId
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public List<RenderJobDataRow> loadAll(final Session session, final int profileId) throws SessionException, DataTableException {
		return dataTable.loadAll(session, profileId);
	}

	/**
	 * @param session
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void deleteAll(final Session session) throws SessionException, DataTableException {
		final List<RenderJobDataRow> jobs = this.loadAll(session);
		for (final RenderJobDataRow job : jobs) {
			delete(session, job);
		}
	}

	/**
	 * @param session
	 * @throws SessionException
	 * @throws DataTableException
	 * @throws IOException
	 */
	public void cleanAll(final Session session) throws SessionException, DataTableException, IOException {
		final List<RenderJobDataRow> jobs = this.loadAll(session);
		for (final RenderJobDataRow job : jobs) {
			clean(session, job);
		}
	}

	/**
	 * @param session
	 * @throws SessionException
	 */
	public void cleanup(final Session session) throws SessionException {
		final File[] files = fileCache.list();
		for (final File file : files) {
			try {
				final int id = Integer.parseInt(file.getName());
				dataTable.load(session, id);
			}
			catch (final NumberFormatException e) {
				throw new SessionException("Can't delete the job: " + file.getName());
			}
			catch (final DataTableException e) {
				DefaultFilesystemCache.deleteDir(file);
			}
		}
	}

	/**
	 * @param jobId
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(final int jobId) throws IOException {
		return fileCache.getInputStream(jobId);
	}

	/**
	 * @param jobId
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream(final int jobId) throws IOException {
		return fileCache.getOutputStream(jobId);
	}

	/**
	 * @param jobId
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getRandomAccessFile(final int jobId) throws IOException {
		return fileCache.getRandomAccessFile(jobId);
	}

	/**
	 * @param session
	 * @param jobId
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public RenderJobDataRow getJob(final Session session, final int jobId) throws SessionException, DataTableException {
		return dataTable.load(session, jobId);
	}

	/**
	 * @param session
	 * @param profileId
	 */
	public void deleteAll(final Session session, final int profileId) throws SessionException, DataTableException {
		dataTable.deleteAll(session, profileId);
	}

	/**
	 * @param session
	 * @param job
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void saveStatus(final Session session, final RenderJobDataRow job) throws SessionException, DataTableException {
		dataTable.saveStatus(session, job);
	}

	/**
	 * @param session
	 * @param job
	 * @throws IOException
	 */
	public void clean(final Session session, final RenderJobDataRow job) throws SessionException, DataTableException, IOException {
		fileCache.delete(job.getJobId());
	}

	/**
	 * @param session
	 * @throws DataTableException
	 * @throws SessionException
	 */
	public void init(final Session session) throws DataTableException, SessionException, IOException {
		if (dataTable.updateTable(session)) {
			fileCache.deleteAll();
		}
		dataTable.init(session);
		// questo codice serve per aggiornare la cache al nuovo formato
		File[] files = fileCache.getWorkdir().listFiles();
		boolean needcleanup = false;
		if (files != null) {
			for (File file : files) {
				if (file.getName().endsWith(".bin")) {
					needcleanup = true;
					file.delete();
				}
			}
			if (needcleanup) {
				cleanAll(session);
			}
		}
		cleanup(session);
	}
}
