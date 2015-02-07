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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.net.DiscoveryService;
import com.nextbreakpoint.nextfractal.net.EventMessage;
import com.nextbreakpoint.nextfractal.net.RequestIDFactory;
import com.nextbreakpoint.nextfractal.net.RequestMessage;
import com.nextbreakpoint.nextfractal.net.ResponseMessage;
import com.nextbreakpoint.nextfractal.net.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.net.ServiceException;
import com.nextbreakpoint.nextfractal.net.ServiceListener;
import com.nextbreakpoint.nextfractal.net.ServiceMessage;
import com.nextbreakpoint.nextfractal.net.ServiceSession;
import com.nextbreakpoint.nextfractal.net.SessionHandler;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractSpoolJobService implements JobService<JobInterface> {
	private static final Logger logger = Logger.getLogger(AbstractSpoolJobService.class.getName());
	private static final long CLEANUP_POLLINGTIME = 60 * 1000L;
	private static final long DISPATCHER_POLLINGTIME = 10 * 1000L;
	private static final long KEEPALIVE_POLLINGTIME = 60 * 1000L;
	private static final long EXECUTOR_POLLINGTIME = 60 * 1000L;
	private static final long MESSAGE_POLLINGTIME = 60 * 1000L;
	private static final int MAX_STARTED_JOBS = 50;
	private static final int MAX_DISPATCHED_JOBS = 20;
	private static final long JOB_TIMEOUT = 60 * 1000L;
	private static final long JOB_LIFETIME = 120 * 1000L;
	private static final long REQUEST_TIMEOUT = 30 * 1000L;
	private static final ThreadFactory factory = new DefaultThreadFactory("Thread", true, Thread.MIN_PRIORITY);
	private final List<JobServiceListener> listeners = new LinkedList<JobServiceListener>();
	private final List<ExecutorTask> tasks = new LinkedList<ExecutorTask>();
	private final HashMap<String, ScheduledJob> scheduledJobs = new HashMap<String, ScheduledJob>();
	private final HashMap<String, ScheduledJob> startedJobs = new HashMap<String, ScheduledJob>();
	private final HashMap<String, ScheduledJob> terminatedJobs = new HashMap<String, ScheduledJob>();
	private final HashMap<String, ScheduledJob> spooledJobs = new HashMap<String, ScheduledJob>();
	private final Set<JobSession> sessionHandlers = new LinkedHashSet<JobSession>();
	private volatile List<ServiceEndpoint> services = new LinkedList<ServiceEndpoint>();
	private final DiscoveryService discoveryService;
	private final JobFactory<? extends DistributedSpoolJobInterface> jobFactory;
	private final Object dispatchMonitor = new Object();
	private final Object serviceMonitor = new Object();
	private final Object messageMonitor = new Object();
	private final Object monitor = new Object();
	private final String serviceName;
	private final Worker worker;
	private Thread thread;
	private boolean running;
	private boolean dispatchDirty;
	private boolean serviceDirty;
	private boolean messageDirty;
	private boolean dirty;
	private volatile int jobCount;
	private final int serviceId;

	/**
	 * @param serviceId
	 * @param serviceName
	 * @param discoveryService
	 * @param jobFactory
	 * @param worker
	 */
	public AbstractSpoolJobService(final int serviceId, final String serviceName, final DiscoveryService discoveryService, final JobFactory<? extends DistributedSpoolJobInterface> jobFactory, final Worker worker) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.discoveryService = discoveryService;
		this.jobFactory = jobFactory;
		this.worker = worker;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#getName()
	 */
	@Override
	public String getName() {
		return serviceName;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#start()
	 */
	@Override
	public void start() {
		if (thread == null) {
			thread = factory.newThread(new ServiceHandler());
			thread.setName(serviceName + " JobService Thread");
			running = true;
			thread.start();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#stop()
	 */
	@Override
	public void stop() {
		if (thread != null) {
			running = false;
			thread.interrupt();
			try {
				thread.join();
			}
			catch (final InterruptedException e) {
			}
			thread = null;
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						spooledJobs.clear();
						scheduledJobs.clear();
						startedJobs.clear();
						terminatedJobs.clear();
					}
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#getJobCount()
	 */
	@Override
	public int getJobCount() {
		synchronized (spooledJobs) {
			return spooledJobs.size();
		}
	}

	private boolean isBusy() {
		return (scheduledJobs.size() != 0) || (startedJobs.size() != 0) || (terminatedJobs.size() != 0);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#deleteJob(java.lang.String)
	 */
	@Override
	public void deleteJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (terminatedJobs) {
					final ScheduledJob scheduledJob = spooledJobs.remove(jobId);
					scheduledJobs.remove(jobId);
					terminatedJobs.remove(jobId);
					if (scheduledJob != null) {
						worker.addTask(new DeleteTask(scheduledJob));
					}
					fireStateChanged(isBusy() ? JobServiceListener.STATUS_BUSY : JobServiceListener.STATUS_IDLE, createMessage());
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#stopJob(java.lang.String)
	 */
	@Override
	public void stopJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					scheduledJobs.remove(jobId);
					final ScheduledJob scheduledJob = startedJobs.remove(jobId);
					if (scheduledJob != null) {
						worker.addTask(new StopTask(scheduledJob));
					}
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#abortJob(java.lang.String)
	 */
	@Override
	public void abortJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					final ScheduledJob scheduledJob = startedJobs.get(jobId);
					if (scheduledJob != null) {
						worker.addTask(new AbortTask(scheduledJob));
					}
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#createJob(com.nextbreakpoint.nextfractal.queue.spool.JobListener)
	 */
	@Override
	public String createJob(final JobListener listener) {
		if (listener == null) {
			throw new NullPointerException("listener == null");
		}
		synchronized (spooledJobs) {
			final DistributedSpoolJobInterface job = jobFactory.createJob(JobIDFactory.newJobId(), listener);
			spooledJobs.put(job.getJobId(), new ScheduledJob(job));
			return job.getJobId();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#setJobData(java.lang.String, JobData, int)
	 */
	@Override
	public void setJobData(final String jobId, final JobData jobData, final int frameNumber) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		if (jobData == null) {
			throw new NullPointerException("jobData == null");
		}
		synchronized (spooledJobs) {
			ScheduledJob job = spooledJobs.get(jobId);
			if (job != null) {
				job.getJob().setJobDataRow(jobData);
				job.getJob().setFirstFrameNumber(frameNumber);
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#runJob(java.lang.String)
	 */
	@Override
	public void runJob(final String jobId) {
		if (jobId == null) {
			throw new NullPointerException("jobId == null");
		}
		synchronized (spooledJobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					synchronized (terminatedJobs) {
						if (terminatedJobs.get(jobId) == null) {
							if (startedJobs.get(jobId) == null) {
								ScheduledJob scheduledJob = scheduledJobs.get(jobId);
								if (scheduledJob != null) {
									worker.addTask(new ResetTask(scheduledJob));
								}
								else {
									scheduledJob = spooledJobs.get(jobId);
									if (scheduledJob != null) {
										scheduledJobs.put(jobId, scheduledJob);
										worker.addTask(new ResetTask(scheduledJob));
									}
								}
							}
						}
						fireStateChanged(isBusy() ? JobServiceListener.STATUS_BUSY : JobServiceListener.STATUS_IDLE, createMessage());
					}
				}
			}
		}
		synchronized (dispatchMonitor) {
			dispatchDirty = true;
			dispatchMonitor.notify();
		}
	}

	private class ServiceHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			boolean interrupted = false;
			fireStateChanged(JobServiceListener.STATUS_BORN, serviceName + " service started");
			final Thread dispatcherThread = factory.newThread(new DispatcherHandler());
			dispatcherThread.setName(serviceName + " Dispatcher Thread");
			final Thread executorThread = factory.newThread(new ExecutorHandler());
			executorThread.setName(serviceName + " Executor Thread");
			final Thread keepaliveThread = factory.newThread(new KeepaliveHandler());
			keepaliveThread.setName(serviceName + " Keepalive Thread");
			final Thread messageThread = factory.newThread(new MessageHandler());
			messageThread.setName(serviceName + " Message Thread");
			try {
				dispatcherThread.start();
				executorThread.start();
				messageThread.start();
				keepaliveThread.start();
				discoveryService.start();
				Iterator<ScheduledJob> jobIterator = null;
				while (running) {
					synchronized (spooledJobs) {
						synchronized (scheduledJobs) {
							synchronized (startedJobs) {
								jobIterator = startedJobs.values().iterator();
								while (jobIterator.hasNext()) {
									final ScheduledJob scheduledJob = jobIterator.next();
									if (scheduledJob.isTerminated()) {
										jobIterator.remove();
										terminatedJobs.put(scheduledJob.getJob().getJobId(), scheduledJob);
										worker.addTask(new StopTask(scheduledJob));
									}
									else if (scheduledJob.isFailed() || ((System.currentTimeMillis() - scheduledJob.getJob().getLastUpdate()) > JOB_TIMEOUT)) {
										jobIterator.remove();
										worker.addTask(new ResetTask(scheduledJob));
									}
								}
								jobIterator = scheduledJobs.values().iterator();
								while (jobIterator.hasNext()) {
									final ScheduledJob scheduledJob = jobIterator.next();
									if (scheduledJob.isTerminated()) {
										jobIterator.remove();
									}
								}
								synchronized (terminatedJobs) {
									jobIterator = terminatedJobs.values().iterator();
									while (jobIterator.hasNext()) {
										final ScheduledJob scheduledJob = jobIterator.next();
										if ((System.currentTimeMillis() - scheduledJob.getJob().getLastUpdate()) > JOB_LIFETIME) {
											jobIterator.remove();
											spooledJobs.remove(scheduledJob.getJob().getJobId());
											worker.addTask(new DeleteTask(scheduledJob));
										}
									}
									fireStateChanged(isBusy() ? JobServiceListener.STATUS_BUSY : JobServiceListener.STATUS_IDLE, createMessage());
								}
							}
						}
					}
					synchronized (serviceMonitor) {
						if (!serviceDirty) {
							serviceMonitor.wait(CLEANUP_POLLINGTIME);
						}
						// else {
						// Thread.yield();
						// }
						serviceDirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				interrupted = true;
			}
			finally {
				discoveryService.stop();
				dispatcherThread.interrupt();
				executorThread.interrupt();
				keepaliveThread.interrupt();
				messageThread.interrupt();
				try {
					dispatcherThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				try {
					executorThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				try {
					keepaliveThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
				try {
					messageThread.join();
				}
				catch (InterruptedException e) {
					interrupted = true;
				}
			}
			fireStateChanged(JobServiceListener.STATUS_DEAD, serviceName + " service stopped");
			if (interrupted) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class DispatcherHandler implements Runnable {
		private final List<ScheduledJob> jobs = new LinkedList<ScheduledJob>();

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					try {
						fetchJobs(jobs);
						services = discoveryService.getEndpoints();
						dispatchJobs(services, jobs);
						jobs.clear();
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (dispatchMonitor) {
						if (!dispatchDirty) {
							dispatchMonitor.wait(DISPATCHER_POLLINGTIME);
						}
						// else {
						// Thread.yield();
						// }
						dispatchDirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private void fetchJobs(final List<ScheduledJob> jobs) {
			synchronized (scheduledJobs) {
				synchronized (startedJobs) {
					for (ScheduledJob scheduledJob : scheduledJobs.values()) {
						if (!scheduledJob.isAborted() && !startedJobs.containsKey(scheduledJob.getJob().getJobId()) && (startedJobs.size() < MAX_STARTED_JOBS)) {
							startedJobs.put(scheduledJob.getJob().getJobId(), scheduledJob);
							jobs.add(scheduledJob);
						}
					}
				}
			}
		}

		private void dispatchJobs(final List<ServiceEndpoint> services, final List<ScheduledJob> jobs) {
			try {
				if (jobs.size() > 0) {
					for (int i = 0; i < services.size(); i++) {
						ServiceEndpoint service = services.get(i);
						if (System.currentTimeMillis() - service.getJobCountLastUpdate() > 30000) {
							try {
								if (jobs.size() == 0) {
									break;
								}
								ScheduledJob job = jobs.remove(0);
								while ((jobs.size() > 0) && job.isStarted()) {
									job = jobs.remove(0);
								}
								if (!job.isStarted()) {
									scheduleJob(job, service);
								}
							}
							catch (final Exception e) {
								e.printStackTrace();
							}
						}
					}
					Thread.sleep(2000);
					for (int i = 0; i < MAX_DISPATCHED_JOBS; i++) {
						try {
							if (jobs.size() == 0) {
								break;
							}
							ServiceEndpoint service = selectEndpoint(services);
							if (service == null) {
								break;
							}
							ScheduledJob job = jobs.remove(0);
							if (!job.isStarted()) {
								scheduleJob(job, service);
							}
						}
						catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private void scheduleJob(final ScheduledJob scheduledJob, final ServiceEndpoint service) throws ServiceException {
			if (scheduledJob.getSession() == null) {
				final ServiceSession session = service.createSession(scheduledJob);
				if (session != null) {
					scheduledJob.setSession(session);
					service.setJobCount(service.getJobCount() + 1); // sono ottimista, mi aspetto che il job verrà elaborato da questo service
				}
			}
			if (scheduledJob.getSession() != null) {
				worker.addTask(new StartTask(scheduledJob));
			}
		}

		private ServiceEndpoint selectEndpoint(final List<ServiceEndpoint> services) {
			if (services.size() > 0) {
				ServiceEndpoint bestService = services.get(0);
				for (int i = 1; i < services.size(); i++) {
					ServiceEndpoint service = services.get(i);
					if (service.getJobCount() < bestService.getJobCount()) {
						bestService = service;
					}
				}
				return bestService;
			}
			return null;
		}
	}

	private class KeepaliveHandler implements Runnable {
		private final List<ScheduledJob> jobs = new LinkedList<ScheduledJob>();

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					try {
						jobs.clear();
						fetchJobs(jobs);
						processJobs(jobs);
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					Thread.sleep(KEEPALIVE_POLLINGTIME);
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private void processJobs(final List<ScheduledJob> jobs) {
			final Iterator<ScheduledJob> jobIterator = jobs.iterator();
			while (jobIterator.hasNext()) {
				ScheduledJob scheduledJob = jobIterator.next();
				scheduledJob.keepalive();
			}
		}

		private void fetchJobs(final List<ScheduledJob> jobs) {
			synchronized (startedJobs) {
				for (ScheduledJob scheduledJob : startedJobs.values()) {
					if (scheduledJob.getSession() != null) {
						jobs.add(scheduledJob);
					}
				}
			}
		}
	}

	private class MessageHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			List<JobSession> tmpSessionHandlers = new LinkedList<JobSession>();
			try {
				while (running) {
					try {
						synchronized (sessionHandlers) {
							tmpSessionHandlers.clear();
							tmpSessionHandlers.addAll(sessionHandlers);
						}
						for (JobSession sessionHandler : tmpSessionHandlers) {
							sessionHandler.update();
						}
						tmpSessionHandlers.clear();
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (messageMonitor) {
						if (!messageDirty) {
							messageMonitor.wait(MESSAGE_POLLINGTIME);
						}
						// else {
						// Thread.yield();
						// }
						messageDirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class ExecutorHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final List<ExecutorTask> tasksToRun = new LinkedList<ExecutorTask>();
			try {
				while (running) {
					try {
						synchronized (tasks) {
							tasksToRun.addAll(tasks);
							tasks.clear();
						}
						for (final ExecutorTask task : tasksToRun) {
							task.run();
							Thread.yield();
						}
						tasksToRun.clear();
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (monitor) {
						if (!dirty) {
							monitor.wait(EXECUTOR_POLLINGTIME);
						}
						dirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private class ExecutorTask implements Runnable {
		private final ServiceSession session;
		private final ServiceMessage message;

		/**
		 * @param session
		 * @param message
		 */
		public ExecutorTask(final ServiceSession session, final ServiceMessage message) {
			this.session = session;
			this.message = message;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (session == null) {
				logger.warning("Failed to send message " + message + ", session is null");
				return;
			}
			try {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Ready to send message " + message + " on session " + session.getSessionId());
				}
				if (!session.isExpired()) {
					session.sendMessage(message);
				}
				else {
					logger.warning("Failed to send message " + message + " on session " + session.getSessionId());
					session.invalidate();
				}
			}
			catch (final Exception e) {
				logger.warning("Failed to send message " + message + " on session " + session.getSessionId());
				session.invalidate();
				e.printStackTrace();
			}
		}
	}

	private class DeleteTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected DeleteTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			job.dispose();
			logger.info(serviceName + ": Job deleted " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class StartTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected StartTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			job.start();
			jobCount += 1;
			logger.info(serviceName + ": Job started " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class StopTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected StopTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			job.stop();
			if (jobCount > 0) {
				jobCount -= 1;
			}
			logger.info(serviceName + ": Job stopped " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class AbortTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected AbortTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			job.abort();
			logger.info(serviceName + ": Job aborted " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class ResetTask implements Runnable {
		private final ScheduledJob job;

		/**
		 * @param job
		 */
		protected ResetTask(final ScheduledJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			job.reset();
			if (jobCount > 0) {
				jobCount -= 1;
			}
			logger.info(serviceName + ": Job resetted " + job.getJob() + " (jobs = " + jobCount + ")");
		}
	}

	private class ScheduledJob implements ServiceListener {
		private final JobSession jobSession;

		/**
		 * @param job
		 */
		public ScheduledJob(final DistributedSpoolJobInterface job) {
			jobSession = new JobSession(job);
		}

		/**
		 * 
		 */
		public void keepalive() {
			jobSession.keepalive();
		}

		/**
		 * @return
		 */
		public boolean isFailed() {
			return jobSession.isFailed();
		}

		/**
		 * @return
		 */
		public boolean isStarted() {
			return jobSession.isStarted();
		}

		/**
		 * @return
		 */
		public boolean isAborted() {
			return jobSession.isAborted();
		}

		/**
		 * @return
		 */
		public boolean isTerminated() {
			return jobSession.isTerminated();
		}

		/**
		 * 
		 */
		public void reset() {
			jobSession.reset();
		}

		/**
		 * 
		 */
		public void start() {
			jobSession.start();
		}

		/**
		 * 
		 */
		public void abort() {
			jobSession.abort();
		}

		/**
		 * 
		 */
		public void stop() {
			jobSession.stop();
		}

		/**
		 * 
		 */
		public void dispose() {
			jobSession.dispose();
		}

		/**
		 * @param session
		 */
		public void setSession(final ServiceSession session) {
			jobSession.setSession(session);
		}

		/**
		 * @return
		 */
		public ServiceSession getSession() {
			return jobSession.getSession();
		}

		/**
		 * @return the job
		 */
		public DistributedSpoolJobInterface getJob() {
			return jobSession.job;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceListener#onMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
			jobSession.onMessage(message);
		}
	}

	private class JobSession implements SessionHandler {
		private final InitializedStatus INITIALIZED_STATUS = new InitializedStatus();
		private final ResettedStatus RESETTED_STATUS = new ResettedStatus();
		private final DisposedStatus DISPOSED_STATUS = new DisposedStatus();
		private final AbortedStatus ABORTED_STATUS = new AbortedStatus();
		private final StartedStatus STARTED_STATUS = new StartedStatus();
		private final StoppedStatus STOPPED_STATUS = new StoppedStatus();
		private final DeletedStatus DELETED_STATUS = new DeletedStatus();
		private final ExpiredStatus EXPIRED_STATUS = new ExpiredStatus();
		private final FailedStatus FAILED_STATUS = new FailedStatus();
		private final WaitingHelloStatus WAITING_HELLO_STATUS = new WaitingHelloStatus();
		private final WaitingPutStatus WAITING_PUT_STATUS = new WaitingPutStatus();
		private final WaitingGetStatus WAITING_GET_STATUS = new WaitingGetStatus();
		private final WaitingEventStatus WAITING_EVENT_STATUS = new WaitingEventStatus();
		private final WaitingAbortStatus WAITING_ABORT_STATUS = new WaitingAbortStatus();
		private final WaitingDeleteStatus WAITING_DELETE_STATUS = new WaitingDeleteStatus();
		private final HelloCompletedStatus HELLO_COMPLETED_STATUS = new HelloCompletedStatus();
		private final PutCompletedStatus PUT_COMPLETED_STATUS = new PutCompletedStatus();
		private final GetCompletedStatus GET_COMPLETED_STATUS = new GetCompletedStatus();
		private final AbortCompletedStatus ABORT_COMPLETED_STATUS = new AbortCompletedStatus();
		private final DeleteCompletedStatus DELETE_COMPLETED_STATUS = new DeleteCompletedStatus();
		private final List<ServiceMessage> messages = new LinkedList<ServiceMessage>();
		private final List<Status> requiredStatusList = new LinkedList<Status>();
		private final List<JobEvent> jobEventList = new LinkedList<JobEvent>();
		private final DistributedSpoolJobInterface job;
		private volatile ServiceSession session;
		private volatile long lastSentMessage;
		private volatile Status status = INITIALIZED_STATUS;
		private volatile Status oldStatus = status;
		private volatile int lastFrameNumber;
		private volatile int frameNumber;

		/**
		 * @param job
		 */
		public JobSession(final DistributedSpoolJobInterface job) {
			this.job = job;
			synchronized (sessionHandlers) {
				sessionHandlers.add(this);
			}
		}

		/**
		 * 
		 */
		public synchronized void keepalive() {
			try {
				if ((session != null) && !session.isExpired()) {
					session.sendKeepAliveMessage();
				}
			}
			catch (ServiceException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @param message
		 */
		@Override
		public synchronized void onMessage(final ServiceMessage message) {
			messages.add(message);
			synchronized (messageMonitor) {
				messageDirty = true;
				messageMonitor.notify();
			}
		}

		/**
		 * 
		 */
		public synchronized void update() {
			status.process();
			if (oldStatus != status) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Job " + job.getJobId() + " status changed from " + oldStatus.getName() + " to " + status.getName());
				}
				oldStatus = status;
				synchronized (messageMonitor) {
					messageDirty = true;
					messageMonitor.notify();
				}
				if (status == FAILED_STATUS) {
					synchronized (serviceMonitor) {
						serviceDirty = true;
						serviceMonitor.notify();
					}
				}
			}
		}

		private boolean isRequestTimeout() {
			return ((System.currentTimeMillis() - lastSentMessage) > REQUEST_TIMEOUT) || isExpired();
		}

		/**
		 * @return
		 */
		public synchronized boolean isTerminated() {
			return job.isTerminated();
		}

		/**
		 * @return
		 */
		public synchronized boolean isAborted() {
			return job.isAborted();
		}

		/**
		 * @return
		 */
		public synchronized boolean isStarted() {
			return job.isStarted();
		}

		/**
		 * 
		 */
		public synchronized void reset() {
			status.reset();
		}

		/**
		 * 
		 */
		public synchronized void start() {
			status.start();
		}

		/**
		 * 
		 */
		public synchronized void abort() {
			status.abort();
		}

		/**
		 * 
		 */
		public synchronized void stop() {
			status.stop();
		}

		/**
		 * 
		 */
		@Override
		public synchronized void dispose() {
			status.dispose();
		}

		/**
		 * @return
		 */
		@Override
		public synchronized boolean isExpired() {
			return (session != null) && session.isExpired();
		}

		/**
		 * @return
		 */
		public synchronized boolean isFailed() {
			return status == FAILED_STATUS;
		}

		/**
		 * @return the session
		 */
		@Override
		public synchronized ServiceSession getSession() {
			return session;
		}

		/**
		 * @param session the session to set
		 */
		@Override
		public synchronized void setSession(final ServiceSession session) {
			if (session == null) {
				throw new IllegalArgumentException("session == null");
			}
			if (this.session != null) {
				throw new IllegalArgumentException("session already set");
			}
			this.session = session;
		}

		private void processFailHelloResponse(final ResponseMessage response) {
			final int jobCount = (Integer) ((Object[]) response.getUserData())[0];
			session.setEndpointJobCount(jobCount);
		}

		private void sendHelloRequest() {
			lastSentMessage = System.currentTimeMillis();
			synchronized (tasks) {
				try {
					final RequestMessage request = createHelloRequest();
					tasks.add(new ExecutorTask(session, request));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void sendAbortRequest() {
			lastSentMessage = System.currentTimeMillis();
			synchronized (tasks) {
				try {
					final RequestMessage request = createAbortRequest(job);
					tasks.add(new ExecutorTask(session, request));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void sendDeleteRequest() {
			lastSentMessage = System.currentTimeMillis();
			synchronized (tasks) {
				try {
					final RequestMessage request = createDeleteRequest(job);
					tasks.add(new ExecutorTask(session, request));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void sendPutRequest() {
			lastSentMessage = System.currentTimeMillis();
			synchronized (tasks) {
				try {
					final RequestMessage request = createPutRequest(job);
					tasks.add(new ExecutorTask(session, request));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private void sendGetRequest(final int frameNumber) {
			lastSentMessage = System.currentTimeMillis();
			synchronized (tasks) {
				try {
					final RequestMessage request = createGetRequest(job, frameNumber);
					tasks.add(new ExecutorTask(session, request));
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		private RequestMessage createPutRequest(final DistributedSpoolJobInterface job) throws Exception {
			final RequestMessage message = new RequestMessage();
			message.setRequestId(RequestIDFactory.newRequestId());
			message.setRequestType(RequestMessage.TYPE_PUT);
			if (!session.isLocalSession()) {
				byte[] jobData = null;
				if (job.getFrameNumber() > 0) {
					ChunkedRandomAccessFile raf = job.getRAF();
					try {
						raf = job.getRAF();
						final int tw = job.getJobDataRow().getTileWidth();
						final int th = job.getJobDataRow().getTileHeight();
						final int bw = job.getJobDataRow().getBorderWidth();
						final int bh = job.getJobDataRow().getBorderHeight();
						final int sw = tw + 2 * bw;
						final int sh = th + 2 * bh;
						final byte[] data = new byte[sw * sh * 4];
						final long pos = job.getFrameNumber() * sw * sh * 4;
						raf.seek(pos);
						raf.readFully(data);
						jobData = data;
					}
					finally {
						if (raf != null) {
							try {
								raf.close();
							}
							catch (final IOException e) {
							}
						}
					}
				}
				final DistributedJobEncoder encoder = new DistributedJobEncoder(job.getSpoolData(), job.getJobDataRow(), jobData);
				message.setUserData(new Object[] { job.getRemoteJobId(), job.getFrameNumber(), encoder.getBytes() });
			}
			else {
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				final ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(job.getJobDataRow());
				oos.close();
				baos.close();
				message.setUserData(new Object[] { job.getRemoteJobId(), job.getFrameNumber(), baos.toByteArray() });
			}
			return message;
		}

		private RequestMessage createHelloRequest() {
			final RequestMessage message = new RequestMessage();
			message.setRequestId(RequestIDFactory.newRequestId());
			message.setRequestType(RequestMessage.TYPE_HELLO);
			return message;
		}

		private RequestMessage createGetRequest(final DistributedSpoolJobInterface job, final int frameNumber) throws Exception {
			final RequestMessage message = new RequestMessage();
			message.setRequestId(RequestIDFactory.newRequestId());
			message.setRequestType(RequestMessage.TYPE_GET);
			message.setUserData(new Object[] { job.getRemoteJobId(), frameNumber });
			return message;
		}

		private RequestMessage createAbortRequest(final DistributedSpoolJobInterface job) throws Exception {
			final RequestMessage message = new RequestMessage();
			message.setRequestId(RequestIDFactory.newRequestId());
			message.setRequestType(RequestMessage.TYPE_ABORT);
			message.setUserData(job.getRemoteJobId());
			return message;
		}

		private RequestMessage createDeleteRequest(final DistributedSpoolJobInterface job) throws Exception {
			final RequestMessage message = new RequestMessage();
			message.setRequestId(RequestIDFactory.newRequestId());
			message.setRequestType(RequestMessage.TYPE_DELETE);
			message.setUserData(job.getRemoteJobId());
			return message;
		}

		private void processHelloResponse(final ResponseMessage response) {
			final int jobCount = (Integer) ((Object[]) response.getUserData())[0];
			final String jobId = (String) ((Object[]) response.getUserData())[1];
			job.setRemoteJobId(jobId);
			session.setEndpointJobCount(jobCount);
		}

		private void processGetResponse(final ResponseMessage response) {
			final int frameNumber = (Integer) ((Object[]) response.getUserData())[1];
			if (!session.isLocalSession()) {
				final byte[] data = (byte[]) ((Object[]) response.getUserData())[2];
				ChunkedRandomAccessFile raf = null;
				final int tw = job.getJobDataRow().getTileWidth();
				final int th = job.getJobDataRow().getTileHeight();
				final int bw = job.getJobDataRow().getBorderWidth();
				final int bh = job.getJobDataRow().getBorderHeight();
				final int sw = tw + 2 * bw;
				final int sh = th + 2 * bh;
				final long pos = frameNumber * sw * sh * 4;
				try {
					raf = job.getRAF();
					raf.seek(pos);
					raf.write(data);
					lastFrameNumber = frameNumber;
					job.setFrameNumber(frameNumber);
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
				finally {
					if (raf != null) {
						try {
							raf.close();
						}
						catch (final IOException e) {
						}
					}
				}
			}
			else {
				lastFrameNumber = frameNumber;
				job.setFrameNumber(frameNumber);
			}
		}

		private void processEvent(final ServiceMessage message) {
			final EventMessage event = (EventMessage) message;
			final JobEvent jobEvent = (JobEvent) event.getUserData();
			jobEventList.add(jobEvent);
		}

		private class InitializedStatus extends Status {
			@Override
			public void update() {
				updateStatus(STARTED_STATUS);
			}

			@Override
			public String getName() {
				return "INITIALIZED";
			}
		}

		private class DeletedStatus extends Status {
			@Override
			public void update() {
				updateStatus(RESETTED_STATUS, STARTED_STATUS);
			}

			@Override
			public String getName() {
				return "DELETED";
			}
		}

		private class FailedStatus extends Status {
			@Override
			public void update() {
				updateStatus(RESETTED_STATUS, STARTED_STATUS);
			}

			@Override
			public String getName() {
				return "FAILED";
			}
		}

		private class DisposedStatus extends Status {
			@Override
			public void update() {
				synchronized (sessionHandlers) {
					sessionHandlers.remove(this);
				}
				job.dispose();
				messages.clear();
				jobEventList.clear();
				if (session != null) {
					session.invalidate();
					session.dispose();
					session = null;
				}
				synchronized (serviceMonitor) {
					serviceDirty = true;
					serviceMonitor.notify();
				}
				synchronized (dispatchMonitor) {
					dispatchDirty = true;
					dispatchMonitor.notify();
				}
			}

			@Override
			public String getName() {
				return "DISPOSED";
			}
		}

		private class ExpiredStatus extends Status {
			@Override
			public void update() {
				status = FAILED_STATUS;
				if (session != null) {
					session.invalidate();
					session.dispose();
					session = null;
				}
				synchronized (serviceMonitor) {
					serviceDirty = true;
					serviceMonitor.notify();
				}
				synchronized (dispatchMonitor) {
					dispatchDirty = true;
					dispatchMonitor.notify();
				}
			}

			@Override
			public void process() {
				update();
			}

			@Override
			public String getName() {
				return "EXPIRED";
			}
		}

		private class StartedStatus extends Status {
			@Override
			public void update() {
				lastFrameNumber = -1;
				frameNumber = -1;
				messages.clear();
				jobEventList.clear();
				job.start();
				status = WAITING_HELLO_STATUS;
				sendHelloRequest();
			}

			@Override
			public String getName() {
				return "STARTED";
			}
		}

		private class StoppedStatus extends Status {
			@Override
			public void update() {
				job.stop();
				if (session != null) {
					status = WAITING_ABORT_STATUS;
					sendAbortRequest();
				}
				else {
					status = DELETE_COMPLETED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "STOPPED";
			}
		}

		private class AbortedStatus extends Status {
			@Override
			public void update() {
				job.abort();
				if (session != null) {
					status = WAITING_ABORT_STATUS;
					sendAbortRequest();
				}
				else {
					status = DELETE_COMPLETED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "ABORTED";
			}
		}

		private class ResettedStatus extends Status {
			@Override
			public void update() {
				job.reset();
				lastFrameNumber = -1;
				frameNumber = -1;
				messages.clear();
				jobEventList.clear();
				if (session != null) {
					status = WAITING_ABORT_STATUS;
					sendAbortRequest();
				}
				else {
					status = DELETE_COMPLETED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "RESETTED";
			}
		}

		private class WaitingAbortStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					for (ServiceMessage message : messages) {
						consumeMessage(message);
					}
					messages.clear();
				}
				if (isRequestTimeout()) {
					status = FAILED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "WAITING_ABORT";
			}

			private void consumeMessage(final ServiceMessage message) {
				try {
					switch (message.getMessageType()) {
						case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
							final ResponseMessage response = (ResponseMessage) message;
							if (response.getReturnCode() != 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							if (response.getReturnCode() == 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									case RequestMessage.TYPE_ABORT: {
										status = ABORT_COMPLETED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_REQUEST: {
							status = FAILED_STATUS;
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_EVENT: {
							status = FAILED_STATUS;
							break;
						}
						default: {
							break;
						}
					}
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		private class WaitingDeleteStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					for (ServiceMessage message : messages) {
						consumeMessage(message);
					}
					messages.clear();
				}
				if (isRequestTimeout()) {
					status = FAILED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "WAITING_DELETE";
			}

			private void consumeMessage(final ServiceMessage message) {
				try {
					switch (message.getMessageType()) {
						case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
							final ResponseMessage response = (ResponseMessage) message;
							if (response.getReturnCode() != 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							if (response.getReturnCode() == 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT: {
										status = FAILED_STATUS;
										break;
									}
									case RequestMessage.TYPE_DELETE: {
										status = DELETE_COMPLETED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_REQUEST: {
							status = FAILED_STATUS;
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_EVENT: {
							status = FAILED_STATUS;
							break;
						}
						default: {
							break;
						}
					}
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		private class WaitingHelloStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					for (ServiceMessage message : messages) {
						consumeMessage(message);
					}
					messages.clear();
				}
				if (isRequestTimeout()) {
					status = FAILED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "WAITING_HELLO";
			}

			private void consumeMessage(final ServiceMessage message) {
				try {
					switch (message.getMessageType()) {
						case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
							final ResponseMessage response = (ResponseMessage) message;
							if (response.getReturnCode() != 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									case RequestMessage.TYPE_HELLO: {
										processFailHelloResponse(response);
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							if (response.getReturnCode() == 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									case RequestMessage.TYPE_HELLO: {
										processHelloResponse(response);
										status = HELLO_COMPLETED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_REQUEST: {
							status = FAILED_STATUS;
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_EVENT: {
							status = FAILED_STATUS;
							break;
						}
						default: {
							break;
						}
					}
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		private class WaitingGetStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					for (ServiceMessage message : messages) {
						consumeMessage(message);
					}
					messages.clear();
				}
				if (isRequestTimeout()) {
					status = FAILED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "WAITING_GET";
			}

			private void consumeMessage(final ServiceMessage message) {
				try {
					switch (message.getMessageType()) {
						case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
							final ResponseMessage response = (ResponseMessage) message;
							if (response.getReturnCode() != 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							if (response.getReturnCode() == 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									case RequestMessage.TYPE_GET: {
										processGetResponse(response);
										status = GET_COMPLETED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_REQUEST: {
							status = FAILED_STATUS;
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_EVENT: {
							processEvent(message);
							break;
						}
						default: {
							break;
						}
					}
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		private class WaitingPutStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					for (ServiceMessage message : messages) {
						consumeMessage(message);
					}
					messages.clear();
				}
				if (isRequestTimeout()) {
					status = FAILED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "WAITING_PUT";
			}

			private void consumeMessage(final ServiceMessage message) {
				try {
					switch (message.getMessageType()) {
						case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
							final ResponseMessage response = (ResponseMessage) message;
							if (response.getReturnCode() != 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							if (response.getReturnCode() == 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									case RequestMessage.TYPE_PUT: {
										status = PUT_COMPLETED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_REQUEST: {
							status = FAILED_STATUS;
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_EVENT: {
							processEvent(message);
							break;
						}
						default: {
							break;
						}
					}
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		private class WaitingEventStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					for (ServiceMessage message : messages) {
						consumeMessage(message);
					}
					messages.clear();
					if (requiredStatusList.size() > 0) {
						updateStatus(ABORTED_STATUS, STOPPED_STATUS);
					}
					else {
						if (jobEventList.size() > 0) {
							JobEvent jobEvent = jobEventList.remove(0);
							switch (jobEvent.getEventType()) {
								case JobEvent.EVENT_TYPE_FRAME: {
									session.setEndpointJobCount(((JobStatus) jobEvent.getEventData()).getJobCount());
									frameNumber = ((JobStatus) jobEvent.getEventData()).getFrameNumber();
									status = WAITING_GET_STATUS;
									sendGetRequest(frameNumber);
									break;
								}
								case JobEvent.EVENT_TYPE_BEGIN: {
									session.setEndpointJobCount(((JobStatus) jobEvent.getEventData()).getJobCount());
									lastFrameNumber = -1;
									frameNumber = -1;
									break;
								}
								case JobEvent.EVENT_TYPE_END: {
									session.setEndpointJobCount(((JobStatus) jobEvent.getEventData()).getJobCount());
									status = WAITING_DELETE_STATUS;
									sendDeleteRequest();
									break;
								}
								case JobEvent.EVENT_TYPE_DONE: {
									session.setEndpointJobCount(((JobStatus) jobEvent.getEventData()).getJobCount());
									if ((frameNumber == lastFrameNumber) && (frameNumber >= 0)) {
										job.terminate();
									}
									break;
								}
								default: {
									break;
								}
							}
							synchronized (messageMonitor) {
								messageDirty = true;
								messageMonitor.notify();
							}
						}
					}
				}
			}

			@Override
			public String getName() {
				return "WAITING_EVENT";
			}

			private void consumeMessage(final ServiceMessage message) {
				try {
					switch (message.getMessageType()) {
						case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
							final ResponseMessage response = (ResponseMessage) message;
							if (response.getReturnCode() != 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							if (response.getReturnCode() == 0) {
								switch (response.getResponseType()) {
									case RequestMessage.TYPE_HELLO:
									case RequestMessage.TYPE_GET:
									case RequestMessage.TYPE_PUT:
									case RequestMessage.TYPE_ABORT:
									case RequestMessage.TYPE_DELETE: {
										status = FAILED_STATUS;
										break;
									}
									default: {
										break;
									}
								}
							}
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_REQUEST: {
							status = FAILED_STATUS;
							break;
						}
						case ServiceMessage.MESSAGE_TYPE_EVENT: {
							processEvent(message);
							break;
						}
						default: {
							break;
						}
					}
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		private class AbortCompletedStatus extends Status {
			@Override
			public void update() {
				if (session != null) {
					status = WAITING_DELETE_STATUS;
					sendDeleteRequest();
				}
				else {
					status = DELETE_COMPLETED_STATUS;
				}
			}

			@Override
			public String getName() {
				return "ABORT_COMPLETED";
			}
		}

		private class DeleteCompletedStatus extends Status {
			@Override
			public void update() {
				status = DELETED_STATUS;
				synchronized (serviceMonitor) {
					serviceDirty = true;
					serviceMonitor.notify();
				}
				synchronized (dispatchMonitor) {
					dispatchDirty = true;
					dispatchMonitor.notify();
				}
			}

			@Override
			public String getName() {
				return "DELETE_COMPLETED";
			}
		}

		private class HelloCompletedStatus extends Status {
			@Override
			public void update() {
				jobEventList.clear();
				if (session != null) {
					status = WAITING_PUT_STATUS;
					sendPutRequest();
				}
			}

			@Override
			public String getName() {
				return "HELLO_COMPLETED";
			}
		}

		private class GetCompletedStatus extends Status {
			@Override
			public void update() {
				status = WAITING_EVENT_STATUS;
				updateStatus(ABORTED_STATUS, STOPPED_STATUS);
			}

			@Override
			public String getName() {
				return "GET_COMPLETED";
			}
		}

		private class PutCompletedStatus extends Status {
			@Override
			public void update() {
				status = WAITING_EVENT_STATUS;
				updateStatus(ABORTED_STATUS, STOPPED_STATUS);
			}

			@Override
			public String getName() {
				return "PUT_COMPLETED";
			}
		}

		private abstract class Status {
			public abstract String getName();

			public abstract void update();

			public void process() {
				if (isExpired()) {
					status = EXPIRED_STATUS;
					return;
				}
				update();
			}

			public final void dispose() {
				requiredStatusList.add(DISPOSED_STATUS);
				synchronized (messageMonitor) {
					messageDirty = true;
					messageMonitor.notify();
				}
			}

			public final void reset() {
				requiredStatusList.add(RESETTED_STATUS);
				synchronized (messageMonitor) {
					messageDirty = true;
					messageMonitor.notify();
				}
			}

			public final void start() {
				requiredStatusList.add(STARTED_STATUS);
				synchronized (messageMonitor) {
					messageDirty = true;
					messageMonitor.notify();
				}
			}

			public final void stop() {
				requiredStatusList.add(STOPPED_STATUS);
				synchronized (messageMonitor) {
					messageDirty = true;
					messageMonitor.notify();
				}
			}

			public final void abort() {
				requiredStatusList.add(ABORTED_STATUS);
				synchronized (messageMonitor) {
					messageDirty = true;
					messageMonitor.notify();
				}
			}
		}

		public void updateStatus(final Status... statusList) {
			Status newStatus = null;
			Iterator<Status> statusIterator = requiredStatusList.iterator();
			while (statusIterator.hasNext()) {
				Status tmpStatus = statusIterator.next();
				for (Status status : statusList) {
					if (status == tmpStatus) {
						newStatus = tmpStatus;
						break;
					}
				}
				statusIterator.remove();
				if (newStatus != null) {
					break;
				}
			}
			if (newStatus != null) {
				status = newStatus;
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#addServiceListener(com.nextbreakpoint.nextfractal.queue.spool.JobServiceListener)
	 */
	@Override
	public void addServiceListener(final JobServiceListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobService#removeServiceListener(com.nextbreakpoint.nextfractal.queue.spool.JobServiceListener)
	 */
	@Override
	public void removeServiceListener(final JobServiceListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param message
	 */
	protected void fireStateChanged(final int status, final String message) {
		for (JobServiceListener listener : listeners) {
			listener.stateChanged(serviceId, status, message);
		}
	}

	private String createMessage() {
		return serviceName + " running (" + services.size() + " " + (services.size() == 1 ? "peer available" : "peers available") + ")";
	}
}
