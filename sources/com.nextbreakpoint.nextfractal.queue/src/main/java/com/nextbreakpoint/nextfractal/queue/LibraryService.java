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
package com.nextbreakpoint.nextfractal.queue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.util.ProgressListener;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipService;
import com.nextbreakpoint.nextfractal.queue.encoder.EncoderContext;
import com.nextbreakpoint.nextfractal.queue.encoder.EncoderException;
import com.nextbreakpoint.nextfractal.queue.encoder.RAFEncoderContext;
import com.nextbreakpoint.nextfractal.queue.extensionPoints.encoder.EncoderExtensionRuntime;
import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.queue.job.RenderJob;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobService;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileService;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipController;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLExporter;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLImporter;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.TwisterSequence;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.converter.ConverterExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class LibraryService {
	private static final Logger logger = Logger.getLogger(LibraryService.class.getName());
	public static final int TILE_WIDTH = 256;
	public static final int TILE_HEIGHT = 256;
	private final List<LibraryServiceListener> listeners = new ArrayList<LibraryServiceListener>();
	private RenderProfileService profileService;
	private RenderJobService jobService;
	private RenderClipService clipService;
	private final File workdir;
	private Session session;

	/**
	 * @param session
	 * @param workdir
	 * @throws RenderServiceException
	 */
	public LibraryService(final Session session, final File workdir) throws RenderServiceException {
		try {
			clipService = new RenderClipService(workdir);
			profileService = new RenderProfileService(workdir);
			jobService = new RenderJobService(workdir);
			this.workdir = workdir;
			this.session = session;
			clipService.init(session);
			profileService.init(session);
			jobService.init(session);
		}
		catch (final Exception e) {
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @return
	 * @throws RenderServiceException
	 */
	public List<RenderClipDataRow> loadClips() throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderClipDataRow> clips = clipService.loadAll(session);
			session.closeTransaction();
			for (RenderClipDataRow clip : clips) {
				fireClipLoaded(clip);
			}
			return clips;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param clip
	 * @param clip
	 * @throws RenderServiceException
	 */
	public void createClip(final RenderClipDataRow clip, final TwisterClip twisterClip) throws RenderServiceException {
		try {
			session.openTransaction();
			clipService.create(session, clip);
			session.closeTransaction();
			if (twisterClip != null) {
				final TwisterClipXMLExporter exporter = new TwisterClipXMLExporter();
				final Document doc = XML.createDocument();
				final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
				final Element element = exporter.exportToElement(twisterClip, builder);
				doc.appendChild(element);
				final OutputStream os = getClipOutputStream(clip.getClipId());
				XML.saveDocument(os, "twister-clip.xml", doc);
				os.close();
			}
			fireClipCreated(clip);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param clip
	 * @throws RenderServiceException
	 */
	public void saveClip(final RenderClipDataRow clip) throws RenderServiceException {
		try {
			session.openTransaction();
			clipService.save(session, clip);
			session.closeTransaction();
			fireClipUpdated(clip);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param clip
	 * @throws RenderServiceException
	 */
	public void deleteClip(final RenderClipDataRow clip) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderProfileDataRow> profiles = profileService.loadAll(session, clip.getClipId());
			for (final RenderProfileDataRow profile : profiles) {
				profileService.delete(session, profile);
			}
			clipService.delete(session, clip);
			session.closeTransaction();
			for (final RenderProfileDataRow profile : profiles) {
				fireProfileDeleted(profile);
			}
			fireClipDeleted(clip);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param clipId
	 * @return
	 * @throws RenderServiceException
	 */
	public RenderClipDataRow getClip(final int clipId) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderClipDataRow clip = clipService.getClip(session, clipId);
			session.closeTransaction();
			return clip;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param clipId
	 * @return
	 * @throws RenderServiceException
	 */
	public List<RenderProfileDataRow> loadProfiles(final int clipId) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderProfileDataRow> profiles = profileService.loadAll(session, clipId);
			session.closeTransaction();
			for (RenderProfileDataRow profile : profiles) {
				fireProfileLoaded(profile);
			}
			return profiles;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param clipId
	 * @return
	 */
	public List<RenderProfileDataRow> resetProfiles(final int clipId) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderProfileDataRow> profiles = profileService.loadAll(session, clipId);
			for (final RenderProfileDataRow profile : profiles) {
				profile.setJobCreated(0);
				profile.setJobStored(0);
				profileService.saveJobs(session, profile);
				profileService.clean(session, profile);
			}
			session.closeTransaction();
			for (final RenderProfileDataRow profile : profiles) {
				fireProfileUpdated(profile);
			}
			return profiles;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profile
	 * @throws RenderServiceException
	 */
	public void createProfile(final RenderProfileDataRow profile) throws RenderServiceException {
		try {
			session.openTransaction();
			profileService.create(session, profile);
			session.closeTransaction();
			fireProfileCreated(profile);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profile
	 * @throws RenderServiceException
	 */
	public void saveProfile(final RenderProfileDataRow profile) throws RenderServiceException {
		try {
			session.openTransaction();
			profileService.save(session, profile);
			session.closeTransaction();
			fireProfileUpdated(profile);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profile
	 * @throws RenderServiceException
	 */
	public void deleteProfile(final RenderProfileDataRow profile) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session, profile.getProfileId());
			for (final RenderJobDataRow job : jobs) {
				jobService.delete(session, job);
			}
			profileService.delete(session, profile);
			session.closeTransaction();
			for (final RenderJobDataRow job : jobs) {
				fireJobDeleted(job);
			}
			fireProfileDeleted(profile);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profileId
	 * @return
	 * @throws RenderServiceException
	 */
	public RenderProfileDataRow getProfile(final int profileId) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderProfileDataRow profile = profileService.getProfile(session, profileId);
			session.closeTransaction();
			return profile;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	// /**
	// * @param profileId
	// * @return
	// * @throws ServiceException
	// */
	// public List<RenderJobDataRow> loadJobs(final int profileId) throws ServiceException {
	// try {
	// session.openTransaction();
	// final List<RenderJobDataRow> jobs = jobService.loadAll(session, profileId);
	// session.closeTransaction();
	// return jobs;
	// }
	// catch (final TransactionException e) {
	// throw new ServiceException(e);
	// }
	// catch (final Exception e) {
	// try {
	// session.abortTransaction();
	// }
	// catch (final TransactionException x) {
	// x.printStackTrace();
	// }
	// throw new ServiceException(e);
	// }
	// }
	/**
	 * @return
	 * @throws RenderServiceException
	 */
	public List<RenderJobDataRow> loadJobs() throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session);
			session.closeTransaction();
			return jobs;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void createJob(final RenderJobDataRow job) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderProfileDataRow profile = profileService.getProfile(session, job.getProfileId());
			final RenderClipDataRow clip = clipService.getClip(session, profile.getClipId());
			clip.setStatus(clip.getStatus() + 1);
			clipService.saveStatus(session, clip);
			profile.setStatus(profile.getStatus() + 1);
			profileService.saveStatus(session, profile);
			jobService.create(session, job);
			startJob(job);
			session.closeTransaction();
			fireProfileUpdated(profile);
			fireClipUpdated(clip);
			fireJobCreated(job);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void saveJob(final RenderJobDataRow job) throws RenderServiceException {
		try {
			session.openTransaction();
			jobService.save(session, job);
			session.closeTransaction();
			fireJobUpdated(job);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void deleteJob(final RenderJobDataRow job) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderProfileDataRow profile = profileService.getProfile(session, job.getProfileId());
			final RenderClipDataRow clip = clipService.getClip(session, profile.getClipId());
			clip.setStatus(clip.getStatus() - 1);
			clipService.saveStatus(session, clip);
			profile.setStatus(profile.getStatus() - 1);
			profileService.saveStatus(session, profile);
			stopJob(job);
			jobService.delete(session, job);
			session.closeTransaction();
			fireProfileUpdated(profile);
			fireClipUpdated(clip);
			fireJobAborted(job);
			fireJobStopped(job);
			fireJobDeleted(job);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param jobId
	 * @return
	 * @throws RenderServiceException
	 */
	public RenderJobDataRow getJob(final int jobId) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderJobDataRow job = jobService.getJob(session, jobId);
			session.closeTransaction();
			return job;
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void saveJobStatus(final RenderJobDataRow job) throws RenderServiceException {
		try {
			session.openTransaction();
			jobService.saveStatus(session, job);
			session.closeTransaction();
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	private void startJob(final RenderJobDataRow job) throws RenderServiceException {
		try {
			job.setStatus(1);
			jobService.saveStatus(session, job);
		}
		catch (final Exception e) {
			throw new RenderServiceException(e);
		}
	}

	private void stopJob(final RenderJobDataRow job) throws RenderServiceException {
		try {
			job.setStatus(0);
			jobService.saveStatus(session, job);
		}
		catch (final Exception e) {
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @throws RenderServiceException
	 */
	public void startJobs() throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session);
			for (final RenderJobDataRow job : jobs) {
				startJob(job);
			}
			session.closeTransaction();
			for (final RenderJobDataRow job : jobs) {
				fireJobStarted(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @throws RenderServiceException
	 */
	public void stopJobs() throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session);
			for (final RenderJobDataRow job : jobs) {
				stopJob(job);
			}
			session.closeTransaction();
			for (final RenderJobDataRow job : jobs) {
				fireJobAborted(job);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobStopped(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @throws RenderServiceException
	 */
	public void deleteJobs() throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session);
			for (final RenderJobDataRow job : jobs) {
				stopJob(job);
				jobService.delete(session, job);
			}
			final List<RenderProfileDataRow> profiles = profileService.loadAll(session);
			for (final RenderProfileDataRow profile : profiles) {
				profile.setStatus(0);
				profileService.saveStatus(session, profile);
			}
			final List<RenderClipDataRow> clips = clipService.loadAll(session);
			for (final RenderClipDataRow clip : clips) {
				clip.setStatus(0);
				clipService.saveStatus(session, clip);
			}
			session.closeTransaction();
			for (final RenderProfileDataRow profile : profiles) {
				fireProfileUpdated(profile);
			}
			for (final RenderClipDataRow clip : clips) {
				fireClipUpdated(clip);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobAborted(job);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobStopped(job);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobDeleted(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profileId
	 * @param listener
	 * @param message
	 * @param percentage
	 * @throws RenderServiceException
	 */
	public void startJobs(final int profileId, final ProgressListener listener, final String message, final float percentage) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session, profileId);
			for (int i = 0; i < jobs.size(); i++) {
				RenderJobDataRow job = jobs.get(i);
				listener.stateChanged(message, (int) Math.rint((((float) i) / (jobs.size() - 1)) * 100f));
				startJob(job);
			}
			session.closeTransaction();
			for (final RenderJobDataRow job : jobs) {
				fireJobStarted(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profileId
	 * @param listener
	 * @param message
	 * @param percentage
	 * @throws RenderServiceException
	 */
	public void stopJobs(final int profileId, final ProgressListener listener, final String message, final float percentage) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session, profileId);
			for (int i = 0; i < jobs.size(); i++) {
				RenderJobDataRow job = jobs.get(i);
				listener.stateChanged(message, (int) Math.rint((((float) i) / (jobs.size() - 1)) * 100f));
				stopJob(job);
			}
			session.closeTransaction();
			for (final RenderJobDataRow job : jobs) {
				fireJobAborted(job);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobStopped(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @throws RenderServiceException
	 */
	public void resumeJobs() throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session);
			session.closeTransaction();
			for (final RenderJobDataRow job : jobs) {
				fireJobResumed(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profileId
	 * @param listener
	 * @param percentage
	 * @throws RenderServiceException
	 */
	public void deleteJobs(final int profileId, final ProgressListener listener, final String message, final float percentage) throws RenderServiceException {
		try {
			session.openTransaction();
			final List<RenderJobDataRow> jobs = jobService.loadAll(session, profileId);
			final RenderProfileDataRow profile = profileService.getProfile(session, profileId);
			final RenderClipDataRow clip = clipService.getClip(session, profile.getClipId());
			for (int i = 0; i < jobs.size(); i++) {
				RenderJobDataRow job = jobs.get(i);
				listener.stateChanged(message, (int) Math.rint((((float) i) / (jobs.size() - 1)) * 100f));
				stopJob(job);
				jobService.delete(session, job);
			}
			clip.setStatus(clip.getStatus() - profile.getStatus());
			clipService.saveStatus(session, clip);
			profile.setStatus(0);
			profileService.saveStatus(session, profile);
			session.closeTransaction();
			fireProfileUpdated(profile);
			fireClipUpdated(clip);
			for (final RenderJobDataRow job : jobs) {
				fireJobAborted(job);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobStopped(job);
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobDeleted(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profileId
	 * @param listener
	 * @param message
	 * @param percentage
	 * @throws RenderServiceException
	 */
	public void createJobs(final int profileId, final ProgressListener listener, final String message, final float percentage) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderProfileDataRow profile = profileService.getProfile(session, profileId);
			final RenderClipDataRow clip = clipService.getClip(session, profile.getClipId());
			profile.setTotalFrames((profile.getStopTime() - profile.getStartTime()) * profile.getFrameRate());
			profile.setJobFrame(0);
			profileService.saveFrames(session, profile);
			final List<RenderJobDataRow> jobs = createJobs(clip, profile, listener, message, percentage);
			session.closeTransaction();
			fireProfileUpdated(profile);
			fireClipUpdated(clip);
			if (LibraryService.logger.isLoggable(Level.INFO)) {
				LibraryService.logger.info("Created " + profile.getJobCreated() + " jobs");
			}
			for (final RenderJobDataRow job : jobs) {
				fireJobCreated(job);
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	private List<RenderJobDataRow> createJobs(final RenderClipDataRow clip, final RenderProfileDataRow profile, final ProgressListener listener, final String message, final float percentage) throws SessionException, DataTableException, RenderServiceException {
		final List<RenderJobDataRow> jobs = new ArrayList<RenderJobDataRow>();
		final int nx = profile.getImageWidth() / LibraryService.TILE_WIDTH;
		final int ny = profile.getImageHeight() / LibraryService.TILE_HEIGHT;
		final int rx = profile.getImageWidth() - nx * LibraryService.TILE_WIDTH;
		final int ry = profile.getImageHeight() - ny * LibraryService.TILE_HEIGHT;
		if ((nx > 0) && (ny > 0)) {
			for (int i = 0; i < nx; i++) {
				for (int j = 0; j < ny; j++) {
					final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
					job.setClipName(profile.getClipName());
					job.setProfileName(profile.getProfileName());
					job.setClipId(profile.getClipId());
					job.setProfileId(profile.getProfileId());
					job.setFrameNumber(profile.getJobFrame());
					job.setImageWidth(profile.getImageWidth());
					job.setImageHeight(profile.getImageHeight());
					job.setTileWidth(LibraryService.TILE_WIDTH);
					job.setTileHeight(LibraryService.TILE_HEIGHT);
					job.setBorderWidth(24);
					job.setBorderHeight(24);
					job.setTileOffsetX(LibraryService.TILE_WIDTH * i);
					job.setTileOffsetY(LibraryService.TILE_HEIGHT * j);
					job.setQuality(profile.getQuality());
					job.setFrameRate(profile.getFrameRate());
					jobs.add(job);
				}
			}
		}
		if (rx > 0) {
			for (int j = 0; j < ny; j++) {
				final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
				job.setClipName(profile.getClipName());
				job.setProfileName(profile.getProfileName());
				job.setClipId(profile.getClipId());
				job.setProfileId(profile.getProfileId());
				job.setFrameNumber(profile.getJobFrame());
				job.setImageWidth(profile.getImageWidth());
				job.setImageHeight(profile.getImageHeight());
				job.setTileWidth(rx);
				job.setTileHeight(LibraryService.TILE_HEIGHT);
				job.setBorderWidth(24);
				job.setBorderHeight(24);
				job.setTileOffsetX(nx * LibraryService.TILE_WIDTH);
				job.setTileOffsetY(LibraryService.TILE_HEIGHT * j);
				job.setQuality(profile.getQuality());
				job.setFrameRate(profile.getFrameRate());
				jobs.add(job);
			}
		}
		if (ry > 0) {
			for (int i = 0; i < nx; i++) {
				final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
				job.setClipName(profile.getClipName());
				job.setProfileName(profile.getProfileName());
				job.setClipId(profile.getClipId());
				job.setProfileId(profile.getProfileId());
				job.setFrameNumber(profile.getJobFrame());
				job.setImageWidth(profile.getImageWidth());
				job.setImageHeight(profile.getImageHeight());
				job.setTileWidth(LibraryService.TILE_WIDTH);
				job.setTileHeight(ry);
				job.setBorderWidth(24);
				job.setBorderHeight(24);
				job.setTileOffsetX(LibraryService.TILE_WIDTH * i);
				job.setTileOffsetY(ny * LibraryService.TILE_HEIGHT);
				job.setQuality(profile.getQuality());
				job.setFrameRate(profile.getFrameRate());
				jobs.add(job);
			}
		}
		if ((rx > 0) && (ry > 0)) {
			final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
			job.setClipName(profile.getClipName());
			job.setProfileName(profile.getProfileName());
			job.setClipId(profile.getClipId());
			job.setProfileId(profile.getProfileId());
			job.setFrameNumber(profile.getJobFrame());
			job.setImageWidth(profile.getImageWidth());
			job.setImageHeight(profile.getImageHeight());
			job.setTileWidth(rx);
			job.setTileHeight(ry);
			job.setBorderWidth(24);
			job.setBorderHeight(24);
			job.setTileOffsetX(nx * LibraryService.TILE_WIDTH);
			job.setTileOffsetY(ny * LibraryService.TILE_HEIGHT);
			job.setQuality(profile.getQuality());
			job.setFrameRate(profile.getFrameRate());
			jobs.add(job);
		}
		profile.setJobCreated(jobs.size());
		profile.setJobStored(0);
		profileService.saveJobs(session, profile);
		for (int i = 0; i < jobs.size(); i++) {
			RenderJobDataRow job = jobs.get(i);
			listener.stateChanged(message, (int) Math.rint((((float) i) / (jobs.size() - 1)) * 100f));
			clip.setStatus(clip.getStatus() + 1);
			clipService.saveStatus(session, clip);
			profile.setStatus(profile.getStatus() + 1);
			profileService.saveStatus(session, profile);
			jobService.create(session, job);
			startJob(job);
		}
		return jobs;
	}

	private List<RenderJobDataRow> createPostProcessJob(final RenderClipDataRow clip, final RenderProfileDataRow profile) throws SessionException, DataTableException, RenderServiceException {
		final List<RenderJobDataRow> jobs = new ArrayList<RenderJobDataRow>();
		final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
		job.setClipName(profile.getClipName());
		job.setProfileName(profile.getProfileName());
		job.setClipId(profile.getClipId());
		job.setProfileId(profile.getProfileId());
		job.setFrameNumber(0);
		job.setFrameRate(profile.getFrameRate());
		job.setStartTime(profile.getStartTime());
		job.setStopTime(profile.getStopTime());
		job.setTileWidth(profile.getImageWidth());
		job.setTileHeight(profile.getImageHeight());
		job.setImageWidth(profile.getImageWidth());
		job.setImageHeight(profile.getImageHeight());
		job.setBorderWidth(0);
		job.setBorderHeight(0);
		job.setTileOffsetX(0);
		job.setTileOffsetY(0);
		job.setJobType(JobData.POST_PROCESS_JOB);
		jobs.add(job);
		clip.setStatus(clip.getStatus() + 1);
		clipService.saveStatus(session, clip);
		profile.setStatus(profile.getStatus() + 1);
		profileService.saveStatus(session, profile);
		jobService.create(session, job);
		return jobs;
	}

	private List<RenderJobDataRow> createCopyProcessJob(final RenderClipDataRow clip, final RenderProfileDataRow profile, final RenderJobDataRow processJob) throws SessionException, DataTableException, RenderServiceException {
		final List<RenderJobDataRow> jobs = new ArrayList<RenderJobDataRow>();
		final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
		job.setClipName(processJob.getClipName());
		job.setProfileName(processJob.getProfileName());
		job.setClipId(processJob.getClipId());
		job.setProfileId(processJob.getProfileId());
		job.setFrameNumber(0);
		job.setFrameRate(processJob.getFrameRate());
		job.setStartTime(processJob.getStartTime());
		job.setStopTime(processJob.getStopTime());
		job.setTileWidth(processJob.getTileWidth());
		job.setTileHeight(processJob.getTileHeight());
		job.setImageWidth(processJob.getImageWidth());
		job.setImageHeight(processJob.getImageHeight());
		job.setBorderWidth(processJob.getBorderWidth());
		job.setBorderHeight(processJob.getBorderHeight());
		job.setTileOffsetX(processJob.getTileOffsetX());
		job.setTileOffsetY(processJob.getTileOffsetY());
		job.setJobType(JobData.COPY_PROCESS_JOB);
		job.setJobId(processJob.getJobId());
		jobs.add(job);
		clip.setStatus(clip.getStatus() + 1);
		clipService.saveStatus(session, clip);
		profile.setStatus(profile.getStatus() + 1);
		profileService.saveStatus(session, profile);
		jobService.save(session, job);
		return jobs;
	}

	private List<RenderJobDataRow> createProcessJob(final RenderClipDataRow clip, final RenderProfileDataRow profile, final RenderJobDataRow processJob) throws SessionException, DataTableException, RenderServiceException {
		final List<RenderJobDataRow> jobs = new ArrayList<RenderJobDataRow>();
		final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
		job.setClipName(processJob.getClipName());
		job.setProfileName(processJob.getProfileName());
		job.setClipId(processJob.getClipId());
		job.setProfileId(processJob.getProfileId());
		job.setFrameNumber(processJob.getFrameNumber());
		job.setFrameRate(processJob.getFrameRate());
		job.setStartTime(processJob.getStartTime());
		job.setStopTime(processJob.getStopTime());
		job.setTileWidth(processJob.getTileWidth());
		job.setTileHeight(processJob.getTileHeight());
		job.setImageWidth(processJob.getImageWidth());
		job.setImageHeight(processJob.getImageHeight());
		job.setBorderWidth(processJob.getBorderWidth());
		job.setBorderHeight(processJob.getBorderHeight());
		job.setTileOffsetX(processJob.getTileOffsetX());
		job.setTileOffsetY(processJob.getTileOffsetY());
		job.setJobType(JobData.PROCESS_JOB);
		job.setJobId(processJob.getJobId());
		jobs.add(job);
		clip.setStatus(clip.getStatus() + 1);
		clipService.saveStatus(session, clip);
		profile.setStatus(profile.getStatus() + 1);
		profileService.saveStatus(session, profile);
		jobService.save(session, job);
		return jobs;
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void processUpdated(final JobData jobData) throws RenderServiceException {
		try {
			session.openTransaction();
			final RenderJobDataRow job = jobService.getJob(session, jobData.getJobId());
			job.setFrameNumber(jobData.getFrameNumber());
			jobService.saveStatus(session, job);
			session.closeTransaction();
			fireJobUpdated(job);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void processTerminated(final JobData jobData) throws RenderServiceException {
		try {
			synchronized (session) {
				session.openTransaction();
				final RenderJobDataRow job = jobService.getJob(session, jobData.getJobId());
				final RenderClipDataRow clip = clipService.getClip(session, job.getClipId());
				final RenderProfileDataRow profile = profileService.getProfile(session, job.getProfileId());
				clip.setStatus(clip.getStatus() - 1);
				clipService.saveStatus(session, clip);
				profile.setStatus(profile.getStatus() - 1);
				profileService.saveStatus(session, profile);
				job.setStatus(0);
				jobService.saveStatus(session, job);
				List<RenderJobDataRow> jobs = null;
				job.setFrameNumber(jobData.getFrameNumber());
				profile.setJobFrame(job.getFrameNumber());
				profileService.saveFrames(session, profile);
				if (job.isCopyProcess()) {
					profile.setJobStored(profile.getJobStored() + 1);
					profileService.saveJobs(session, profile);
					jobService.delete(session, job);
				}
				if (job.isCopyProcess() && (profile.getJobStored() == profile.getJobCreated())) {
					jobs = createPostProcessJob(clip, profile);
					for (final RenderJobDataRow createdJob : jobs) {
						startJob(createdJob);
					}
				}
				else if (job.isProcess() && ((job.getFrameRate() == 0) || (job.getFrameNumber() == (profile.getTotalFrames() - 1)))) {
					jobs = createCopyProcessJob(clip, profile, job);
					for (final RenderJobDataRow createdJob : jobs) {
						startJob(createdJob);
					}
				}
				else if (job.isProcess()) {
					jobs = createProcessJob(clip, profile, job);
					for (final RenderJobDataRow createdJob : jobs) {
						startJob(createdJob);
					}
				}
				else if (job.isPostProcess()) {
					jobService.delete(session, job);
				}
				session.closeTransaction();
				fireProfileUpdated(profile);
				fireClipUpdated(clip);
				fireJobDeleted(job);
				if (jobs != null) {
					for (final RenderJobDataRow createdJob : jobs) {
						fireJobCreated(createdJob);
						fireJobStarted(createdJob);
					}
				}
			}
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void processStarted(final JobData jobData) throws RenderServiceException {
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void processStopped(final JobData jobData) throws RenderServiceException {
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void processDeleted(final JobData jobData) throws RenderServiceException {
		// try {
		// synchronized (session) {
		// session.openTransaction();
		// final RenderJobDataRow job = jobService.getJob(session, jobData.getJobId());
		// if (jobData.getJobType() == JobData.COPY_PROCESS_JOB || jobData.getJobType() == JobData.POST_PROCESS_JOB) {
		// jobService.delete(session, job);
		// }
		// session.closeTransaction();
		// if (jobData.getJobType() == JobData.COPY_PROCESS_JOB || jobData.getJobType() == JobData.POST_PROCESS_JOB) {
		// fireJobDeleted(job);
		// }
		// }
		// }
		// catch (final TransactionException e) {
		// throw new ServiceException(e);
		// }
		// catch (final Exception e) {
		// try {
		// session.abortTransaction();
		// }
		// catch (final TransactionException x) {
		// x.printStackTrace();
		// }
		// throw new ServiceException(e);
		// }
	}

	/**
	 * @throws RenderServiceException
	 */
	public void deleteAll() throws RenderServiceException {
		try {
			session.openTransaction();
			jobService.deleteAll(session);
			profileService.deleteAll(session);
			clipService.deleteAll(session);
			session.closeTransaction();
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param dataRow
	 * @param file
	 * @throws RenderServiceException
	 */
	public void importClip(final RenderClipDataRow dataRow, final File file) throws RenderServiceException {
		try {
			session.openTransaction();
			TwisterClip clip = null;
			try {
				final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
				final InputStream is = new FileInputStream(file);
				Document doc = XML.loadDocument(is, "twister-clip.xml");
				clip = importer.importFromElement(doc.getDocumentElement());
				is.close();
			}
			catch (Exception e) {
				List<Extension<ConverterExtensionRuntime>> extensions = TwisterRegistry.getInstance().getConverterRegistry().getExtensionList();
				for (Extension<ConverterExtensionRuntime> extension : extensions) {
					try {
						ConverterExtensionRuntime runtime = extension.createExtensionRuntime();
						TwisterConfig config = runtime.createConverter().convert(file);
						if (config != null) {
							clip = new TwisterClip();
							clip.addSequence(new TwisterSequence());
							clip.getSequence(0).setDuration(0);
							clip.getSequence(0).setInitialConfig(config);
						}
					}
					catch (Exception x) {
						x.printStackTrace();
					}
				}
				if (clip == null) {
					throw e;
				}
			}
			final TwisterClipController controller = new TwisterClipController(clip);
			controller.init();
			dataRow.setDuration(controller.getDuration());
			clipService.save(session, dataRow);
			final TwisterClipXMLExporter exporter = new TwisterClipXMLExporter();
			Document doc = XML.createDocument();
			final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
			final Element element = exporter.exportToElement(clip, builder);
			doc.appendChild(element);
			final OutputStream os = clipService.getOutputStream(dataRow.getClipId());
			XML.saveDocument(os, "twister-clip.xml", doc);
			os.close();
			session.closeTransaction();
			fireClipLoaded(dataRow);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param dataRow
	 * @param file
	 * @throws RenderServiceException
	 */
	public void exportClip(final RenderClipDataRow dataRow, final File file) throws RenderServiceException {
		try {
			final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
			final InputStream is = clipService.getInputStream(dataRow.getClipId());
			Document doc = XML.loadDocument(is, "twister-clip.xml");
			final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
			is.close();
			final TwisterClipXMLExporter exporter = new TwisterClipXMLExporter();
			doc = XML.createDocument();
			final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
			final Element element = exporter.exportToElement(clip, builder);
			doc.appendChild(element);
			final OutputStream os = new FileOutputStream(file);
			XML.saveDocument(os, "twister-clip.xml", doc);
			os.close();
		}
		catch (final Exception e) {
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param dataRow
	 * @return
	 * @throws RenderServiceException
	 */
	public TwisterClip loadClip(final RenderClipDataRow dataRow) throws RenderServiceException {
		try {
			final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
			final InputStream is = clipService.getInputStream(dataRow.getClipId());
			Document doc = XML.loadDocument(is, "twister-clip.xml");
			final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
			is.close();
			return clip;
		}
		catch (final Exception e) {
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profile
	 * @param encoder
	 * @param listener
	 * @param path
	 * @throws RenderServiceException
	 */
	public void exportProfile(final RenderProfileDataRow profile, final EncoderExtensionRuntime<?> encoder, final ProgressListener listener, final File path) throws RenderServiceException {
		encoder.addProgressListener(listener);
		try {
			final EncoderContext context = new DefaultEncoderContext(profile);
			encoder.encode(context, path);
			listener.done();
		}
		catch (final EncoderException e) {
			listener.failed(e);
			throw new RenderServiceException(e);
		}
		catch (final IOException e) {
			listener.failed(e);
			throw new RenderServiceException(e);
		}
		encoder.removeProgressListener(listener);
	}

	/**
	 * @param listener
	 */
	public synchronized void addServiceListener(final LibraryServiceListener listener) {
		listeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public synchronized void removeServiceListener(final LibraryServiceListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param clip
	 */
	protected synchronized void fireClipCreated(final RenderClipDataRow clip) {
		for (final LibraryServiceListener listener : listeners) {
			listener.clipCreated(clip);
		}
	}

	/**
	 * @param clip
	 */
	protected synchronized void fireClipDeleted(final RenderClipDataRow clip) {
		for (final LibraryServiceListener listener : listeners) {
			listener.clipDeleted(clip);
		}
	}

	/**
	 * @param clip
	 */
	protected synchronized void fireClipUpdated(final RenderClipDataRow clip) {
		for (final LibraryServiceListener listener : listeners) {
			listener.clipUpdated(clip);
		}
	}

	/**
	 * @param clip
	 */
	protected synchronized void fireClipLoaded(final RenderClipDataRow clip) {
		for (final LibraryServiceListener listener : listeners) {
			listener.clipLoaded(clip);
		}
	}

	/**
	 * @param profile
	 */
	protected synchronized void fireProfileCreated(final RenderProfileDataRow profile) {
		for (final LibraryServiceListener listener : listeners) {
			listener.profileCreated(profile);
		}
	}

	/**
	 * @param profile
	 */
	protected synchronized void fireProfileDeleted(final RenderProfileDataRow profile) {
		for (final LibraryServiceListener listener : listeners) {
			listener.profileDeleted(profile);
		}
	}

	/**
	 * @param profile
	 */
	protected synchronized void fireProfileUpdated(final RenderProfileDataRow profile) {
		for (final LibraryServiceListener listener : listeners) {
			listener.profileUpdated(profile);
		}
	}

	/**
	 * @param profile
	 */
	protected synchronized void fireProfileLoaded(final RenderProfileDataRow profile) {
		for (final LibraryServiceListener listener : listeners) {
			listener.profileLoaded(profile);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobCreated(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobCreated(job);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobDeleted(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobDeleted(job);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobUpdated(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobUpdated(job);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobStarted(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobStarted(job);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobAborted(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobAborted(job);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobStopped(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobStopped(job);
		}
	}

	/**
	 * @param job
	 */
	protected synchronized void fireJobResumed(final RenderJobDataRow job) {
		for (final LibraryServiceListener listener : listeners) {
			listener.jobResumed(job);
		}
	}

	/**
	 * @param clipId
	 * @return
	 * @throws IOException
	 */
	public OutputStream getClipOutputStream(final int clipId) throws IOException {
		return clipService.getOutputStream(clipId);
	}

	/**
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	public OutputStream getProfileOutputStream(final int profileId) throws IOException {
		return profileService.getOutputStream(profileId);
	}

	/**
	 * @param jobId
	 * @return
	 * @throws IOException
	 */
	public OutputStream getJobOutputStream(final int jobId) throws IOException {
		return jobService.getOutputStream(jobId);
	}

	/**
	 * @param clipId
	 * @return
	 * @throws IOException
	 */
	public InputStream getClipInputStream(final int clipId) throws IOException {
		return clipService.getInputStream(clipId);
	}

	/**
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	public InputStream getProfileInputStream(final int profileId) throws IOException {
		return profileService.getInputStream(profileId);
	}

	/**
	 * @param jobId
	 * @return
	 * @throws IOException
	 */
	public InputStream getJobInputStream(final int jobId) throws IOException {
		return jobService.getInputStream(jobId);
	}

	/**
	 * @param clip
	 * @throws RenderServiceException
	 */
	public void cleanClip(final RenderClipDataRow clip) throws RenderServiceException {
		try {
			session.openTransaction();
			clipService.clean(session, clip);
			session.closeTransaction();
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profile
	 * @throws RenderServiceException
	 */
	public void cleanProfile(final RenderProfileDataRow profile) throws RenderServiceException {
		try {
			session.openTransaction();
			profileService.clean(session, profile);
			session.closeTransaction();
			fireProfileUpdated(profile);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param job
	 * @throws RenderServiceException
	 */
	public void cleanJob(final RenderJobDataRow job) throws RenderServiceException {
		try {
			session.openTransaction();
			jobService.clean(session, job);
			session.closeTransaction();
			fireJobUpdated(job);
		}
		catch (final TransactionException e) {
			throw new RenderServiceException(e);
		}
		catch (final Exception e) {
			try {
				session.abortTransaction();
			}
			catch (final TransactionException x) {
				x.printStackTrace();
			}
			throw new RenderServiceException(e);
		}
	}

	/**
	 * @param profileId
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getProfileRandomAccessFile(final int profileId) throws IOException {
		return profileService.getRandomAccessFile(profileId);
	}

	/**
	 * @param jobId
	 * @return
	 * @throws IOException
	 */
	public ChunkedRandomAccessFile getJobRandomAccessFile(final int jobId) throws IOException {
		return jobService.getRandomAccessFile(jobId);
	}

	/**
	 * @return
	 */
	public File getWorkspace() {
		return workdir;
	}

	private class DefaultEncoderContext extends RAFEncoderContext {
		/**
		 * @param profile
		 * @throws IOException
		 */
		public DefaultEncoderContext(final RenderProfileDataRow profile) throws IOException {
			super(profileService.getRandomAccessFile(profile.getProfileId()), profile.getImageWidth(), profile.getImageHeight(), profile.getFrameRate(), (profile.getStopTime() - profile.getStartTime()) * profile.getFrameRate());
		}
	}
}
