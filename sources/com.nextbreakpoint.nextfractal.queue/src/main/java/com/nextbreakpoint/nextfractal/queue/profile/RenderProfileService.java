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
package com.nextbreakpoint.nextfractal.queue.profile;

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
public class RenderProfileService {
	private final RenderProfileFilesystemCache fileCache;
	private final RenderProfileDataTable dataTable;

	/**
	 * @param workdir
	 * @throws IOException
	 */
	public RenderProfileService(final File workdir) throws IOException {
		fileCache = new RenderProfileFilesystemCache(new File(workdir, "profile"));
		dataTable = new RenderProfileDataTable();
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void create(final Session session, final RenderProfileDataRow profile) throws SessionException, DataTableException {
		dataTable.create(session, profile);
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void delete(final Session session, final RenderProfileDataRow profile) throws SessionException, DataTableException {
		dataTable.delete(session, profile);
		try {
			fileCache.delete(profile.getProfileId());
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void save(final Session session, final RenderProfileDataRow profile) throws SessionException, DataTableException {
		dataTable.save(session, profile);
	}

	/**
	 * @param session
	 * @param id
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public boolean validate(final Session session, final int id) throws SessionException, DataTableException {
		final RenderProfileDataRow profile = dataTable.load(session, id);
		return fileCache.exists(profile.getProfileId());
	}

	/**
	 * @param session
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public List<RenderProfileDataRow> loadAll(final Session session) throws SessionException, DataTableException {
		return dataTable.loadAll(session);
	}

	/**
	 * @param session
	 * @param clipId
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public List<RenderProfileDataRow> loadAll(final Session session, final int clipId) throws SessionException, DataTableException {
		return dataTable.loadAll(session, clipId);
	}

	/**
	 * @param session
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void deleteAll(final Session session) throws SessionException, DataTableException {
		final List<RenderProfileDataRow> profiles = this.loadAll(session);
		for (final RenderProfileDataRow profile : profiles) {
			delete(session, profile);
		}
	}

	/**
	 * @param session
	 * @throws SessionException
	 * @throws DataTableException
	 * @throws IOException
	 */
	public void cleanAll(final Session session) throws SessionException, DataTableException, IOException {
		final List<RenderProfileDataRow> profiles = this.loadAll(session);
		for (final RenderProfileDataRow profile : profiles) {
			clean(session, profile);
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
				throw new SessionException("Can't delete the profile: " + file.getName());
			}
			catch (final DataTableException e) {
				DefaultFilesystemCache.deleteDir(file);
			}
		}
	}

	/**
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream(final int profileId) throws IOException {
		return fileCache.getInputStream(profileId);
	}

	/**
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream(final int profileId) throws IOException {
		return fileCache.getOutputStream(profileId);
	}

	/**
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getRandomAccessFile(final int profileId) throws IOException {
		return fileCache.getRandomAccessFile(profileId);
	}

	/**
	 * @param session
	 * @param profileId
	 * @return
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public RenderProfileDataRow getProfile(final Session session, final int profileId) throws SessionException, DataTableException {
		return dataTable.load(session, profileId);
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void saveStatus(final Session session, final RenderProfileDataRow profile) throws SessionException, DataTableException {
		dataTable.saveStatus(session, profile);
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void saveFrames(final Session session, final RenderProfileDataRow profile) throws DataTableException, SessionException {
		dataTable.saveFrames(session, profile);
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 */
	public void saveJobs(final Session session, final RenderProfileDataRow profile) throws SessionException, DataTableException {
		dataTable.saveJobs(session, profile);
	}

	/**
	 * @param session
	 * @param profile
	 * @throws SessionException
	 * @throws DataTableException
	 * @throws IOException
	 */
	public void clean(final Session session, final RenderProfileDataRow profile) throws SessionException, DataTableException, IOException {
		profile.setJobCreated(0);
		profile.setJobStored(0);
		saveJobs(session, profile);
		fileCache.delete(profile.getProfileId());
	}

	/**
	 * @param session
	 * @throws DataTableException
	 * @throws SessionException
	 * @throws IOException
	 */
	public void init(final Session session) throws DataTableException, SessionException, IOException {
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
