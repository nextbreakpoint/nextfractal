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
package com.nextbreakpoint.nextfractal.test;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.util.ConnectionFactory;
import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.ProgressListener;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.queue.DefaultConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.LibraryServiceListener;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClip;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.extensions.encoder.JPEGEncoderConfig;
import com.nextbreakpoint.nextfractal.queue.extensions.encoder.JPEGEncoderRuntime;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.jxta.JXTADiscoveryService;
import com.nextbreakpoint.nextfractal.queue.jxta.JXTANetworkService;
import com.nextbreakpoint.nextfractal.queue.jxta.JXTASpoolJobService;
import com.nextbreakpoint.nextfractal.queue.network.spool.DistributedServiceProcessor;
import com.nextbreakpoint.nextfractal.queue.network.spool.LocalServiceProcessor;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfile;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;
import com.nextbreakpoint.nextfractal.queue.spool.DefaultDistributedJobService;
import com.nextbreakpoint.nextfractal.queue.spool.DefaultJobService;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;
import com.nextbreakpoint.nextfractal.queue.spool.JobListener;
import com.nextbreakpoint.nextfractal.queue.spool.job.DistributedJob;
import com.nextbreakpoint.nextfractal.queue.spool.job.DistributedJobFactory;
import com.nextbreakpoint.nextfractal.queue.spool.job.DistributedSpoolJobFactory;
import com.nextbreakpoint.nextfractal.queue.spool.job.LocalSpoolJob;
import com.nextbreakpoint.nextfractal.queue.spool.job.LocalSpoolJobFactory;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLExporter;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterSequence;

/**
 * @author Andrea Medeghini
 */
public class SpoolJobServiceTest {
	private static final Logger logger = Logger.getLogger(SpoolJobServiceTest.class.getName());

	private void deleteFiles(final File path) {
		final File[] files = path.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				deleteFiles(file);
			}
			file.delete();
		}
	}

	@Test
	public void testSpool() throws Exception {
		@SuppressWarnings("unused")
		final Surface surface = new Surface(200, 200);
		final File workDir = new File("workdir");
		final File tmpDir = new File(workDir, "tmp");
		tmpDir.mkdirs();
		deleteFiles(tmpDir);
		final ConnectionFactory factory = new DefaultConnectionFactory(workDir);
		final Session session = new Session(factory);
		final LibraryService service = new LibraryService(session, workDir);
		final RenderClipDataRow clipDataRow = new RenderClipDataRow(new RenderClip());
		clipDataRow.setClipName("test clip");
		service.createClip(clipDataRow, null);
		final RenderProfileDataRow renderProfile = new RenderProfileDataRow(new RenderProfile());
		renderProfile.setProfileName("test profile");
		renderProfile.setClipName(clipDataRow.getClipName());
		renderProfile.setClipId(clipDataRow.getClipId());
		renderProfile.setImageWidth(640);
		renderProfile.setImageHeight(480);
		renderProfile.setOffsetX(0);
		renderProfile.setOffsetY(0);
		renderProfile.setStartTime(0);
		renderProfile.setStopTime(0);
		service.createProfile(renderProfile);
		final TwisterClip clip = new TwisterClip();
		final TwisterSequence sequence = new TwisterSequence();
		clip.addSequence(sequence);
		final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
		final TwisterConfig config = configBuilder.createDefaultConfig();
		sequence.setFinalConfig(config);
		final TwisterClipXMLExporter exporter = new TwisterClipXMLExporter();
		final Document doc = XML.createDocument();
		final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(doc);
		final Element element = exporter.exportToElement(clip, builder);
		doc.appendChild(element);
		final OutputStream os = service.getClipOutputStream(clipDataRow.getClipId());
		XML.saveDocument(os, "twister-clip.xml", doc);
		os.close();
		Worker worker = new Worker(new DefaultThreadFactory("TestSpool Worker", true, Thread.MIN_PRIORITY));
		final DistributedServiceProcessor processor1 = new DistributedServiceProcessor(new DefaultDistributedJobService<DistributedJob>(0, "DistributedProcessor", new DistributedJobFactory(tmpDir, worker), worker), 10);
		final LocalServiceProcessor processor2 = new LocalServiceProcessor(new DefaultJobService<LocalSpoolJob>(0, "LocalProcessor", new LocalSpoolJobFactory(service, worker), worker), 10);
		final JXTASpoolJobService jobService = new JXTASpoolJobService(0, new JXTADiscoveryService(new JXTANetworkService(tmpDir, "http://nextfractal.sf.net", "JXTASpool", "Andrea Medeghini", "1.0", processor1), processor2), new DistributedSpoolJobFactory(service, worker), worker);
		processor1.start();
		processor2.start();
		jobService.start();
		worker.start();
		final HashMap<Integer, String> jobs = new HashMap<Integer, String>();
		service.addServiceListener(new LibraryServiceListener() {
			@Override
			public void clipCreated(final RenderClipDataRow clip) {
			}

			@Override
			public void clipDeleted(final RenderClipDataRow clip) {
			}

			@Override
			public void clipUpdated(final RenderClipDataRow clip) {
			}

			@Override
			public void clipLoaded(final RenderClipDataRow clip) {
			}

			@Override
			public void jobCreated(final RenderJobDataRow job) {
				final String jobId = jobService.createJob(new TestListener());
				jobService.setJobData(jobId, job, job.getFrameNumber());
				jobs.put(job.getJobId(), jobId);
				logger.info("Job " + jobId + " created");
			}

			@Override
			public void jobDeleted(final RenderJobDataRow job) {
				final String jobId = jobs.get(job.getJobId());
				jobService.deleteJob(jobId);
				logger.info("Job " + jobId + " deleted");
			}

			@Override
			public void jobStarted(final RenderJobDataRow job) {
				final String jobId = jobs.get(job.getJobId());
				jobService.runJob(jobId);
				logger.info("Job " + jobId + " started");
			}

			@Override
			public void jobStopped(final RenderJobDataRow job) {
				final String jobId = jobs.get(job.getJobId());
				jobService.stopJob(jobId);
				logger.info("Job " + jobId + " stopped");
			}

			@Override
			public void jobAborted(final RenderJobDataRow job) {
			}

			@Override
			public void jobUpdated(final RenderJobDataRow job) {
			}

			@Override
			public void jobResumed(final RenderJobDataRow job) {
			}

			@Override
			public void profileCreated(final RenderProfileDataRow profile) {
			}

			@Override
			public void profileDeleted(final RenderProfileDataRow profile) {
			}

			@Override
			public void profileUpdated(final RenderProfileDataRow profile) {
			}

			@Override
			public void profileLoaded(final RenderProfileDataRow profile) {
			}
		});
		ProgressListener listener = new ProgressListener() {
			@Override
			public void done() {
			}

			@Override
			public void failed(final Throwable e) {
			}

			@Override
			public void stateChanged(final String message, final int percentage) {
			}

			@Override
			public void stateChanged(final String message) {
			}
		};
		service.createJobs(renderProfile.getProfileId(), listener, "Creating jobs", 100f);
		service.startJobs(renderProfile.getProfileId(), listener, "Creating jobs", 100f);
		Thread.sleep(5000);
		try {
			while (true) {
				// boolean terminated = true;
				// for (final DistributedSpoolJob job : jobs.values()) {
				// if (!job.isTerminated()) {
				// terminated = false;
				// }
				// }
				// if (terminated) {
				// break;
				// }
				Thread.sleep(60000);
			}
		}
		catch (final InterruptedException e) {
		}
		service.stopJobs(renderProfile.getProfileId(), listener, "Creating jobs", 100f);
		// for (final DistributedSpoolJob job : jobs.values()) {
		// service.jobCompleted(job.getJobDataRow());
		// }
		final JPEGEncoderRuntime encoder = new JPEGEncoderRuntime();
		encoder.setConfig(new JPEGEncoderConfig());
		service.exportProfile(renderProfile, encoder, listener, new File("test.jpeg"));
		worker.stop();
		jobService.stop();
		processor1.stop();
		processor2.stop();
		session.close();
	}

	private static class TestListener implements JobListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#updated(String, JobData)
		 */
		@Override
		public void updated(final String jobId, final JobData job) {
			logger.info("Job state changed " + job);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#started(String, JobData)
		 */
		@Override
		public void started(final String jobId, final JobData job) {
			logger.info("Job started " + job);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#stopped(String, JobData)
		 */
		@Override
		public void stopped(final String jobId, final JobData job) {
			logger.info("Job stopped " + job);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#terminated(String, JobData)
		 */
		@Override
		public void terminated(final String jobId, final JobData job) {
			logger.info("Job terminated " + job);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#disposed(String, JobData)
		 */
		@Override
		public void disposed(final String jobId, final JobData job) {
			logger.info("Job disposed " + job);
		}
	}
}
