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
package com.nextbreakpoint.nextfractal.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;

/**
 * @author Andrea Medeghini
 */
public class LocalService {
	private static final Logger logger = Logger.getLogger(LocalService.class.getName());
	private static final ThreadFactory factory = new DefaultThreadFactory("Thread", true, Thread.MIN_PRIORITY);
	private final HashMap<String, LocalServiceSession> serverSessions = new HashMap<String, LocalServiceSession>();
	private final HashMap<String, LocalServiceSession> clientSessions = new HashMap<String, LocalServiceSession>();
	private final ServiceProcessor processor;
	private final String serviceName;
	private Thread thread;
	private boolean running;
	private boolean dirty;
	private final Object monitor = new Object();

	/**
	 * @param serviceName
	 * @param processor
	 */
	public LocalService(final String serviceName, final ServiceProcessor processor) {
		this.serviceName = "LocalService " + serviceName;
		this.processor = processor;
	}

	/**
	 * @return the name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("serviceName = ");
		builder.append(serviceName);
		return builder.toString();
	}

	/**
	 * 
	 */
	public void start() {
		processor.start();
		if (thread == null) {
			thread = factory.newThread(new CleanupHandler());
			thread.setName("LocalService Cleanup Thread");
			running = true;
			thread.start();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		processor.stop();
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
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#createSession(com.nextbreakpoint.nextfractal.queue.network.ServiceListener)
	 */
	public ServiceSession createSession(final ServiceListener listener) throws ServiceException {
		final SessionHandler sessionHandler = processor.createSessionHandler();
		final ServiceConsumer clientConsumer = new LocalServiceConsumer(listener);
		final ServiceConsumer serverConsumer = new LocalServiceConsumer(sessionHandler);
		final LocalServiceSession clientSession = new LocalServiceSession(SessionIDFactory.newSessionId(), clientConsumer, new LocalServiceProducer(serverConsumer));
		logger.info("Local client session created " + clientSession.getSessionId());
		synchronized (clientSessions) {
			clientSessions.put(clientSession.getSessionId(), clientSession);
		}
		final LocalServiceSession serverSession = new LocalServiceSession(SessionIDFactory.newSessionId(), serverConsumer, new LocalServiceProducer(clientConsumer));
		logger.info("Local server session created " + serverSession.getSessionId());
		sessionHandler.setSession(serverSession);
		synchronized (serverSessions) {
			serverSessions.put(serverSession.getSessionId(), serverSession);
		}
		return clientSession;
	}

	private class LocalServiceSession extends ServiceSession {
		/**
		 * @param sessionId
		 * @param consumer
		 * @param producer
		 */
		public LocalServiceSession(final String sessionId, final ServiceConsumer consumer, final ServiceProducer producer) {
			super(sessionId, consumer, producer);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceSession#dispose()
		 */
		@Override
		public void dispose() {
			logger.fine("Session disposed " + getSessionId());
			super.dispose();
			synchronized (monitor) {
				dirty = true;
				monitor.notify();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceSession#isLocalSession()
		 */
		@Override
		public boolean isLocalSession() {
			return true;
		}

//		/**
//		 * @param message
//		 * @throws Exception
//		 */
//		public void onMessage(final ServiceMessage message) throws ServiceException {
//			if (isExpired()) {
//				throw new ServiceException("Expired session " + sessionId);
//			}
//			consumer.onMessage(message);
//		}
//
//		/**
//		 * @param message
//		 * @throws Exception
//		 */
//		public void consumeMessage(final ServiceMessage message) throws ServiceException {
//			if (isExpired()) {
//				throw new ServiceException("Expired session " + sessionId);
//			}
//			consumer.consumeMessage(message);
//		}

		/**
		 * @throws Exception
		 */
		public void consumeMessages() throws ServiceException {
			if (isExpired()) {
				throw new ServiceException("Expired session " + sessionId);
			}
			consumer.consumeMessages();
		}
	}

	private class LocalServiceProducer implements ServiceProducer {
		private ServiceListener listener;

		/**
		 * @param listener
		 */
		public LocalServiceProducer(final ServiceListener listener) {
			this.listener = listener;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#dispose()
		 */
		@Override
		public void dispose() {
			listener = null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void sendMessage(final ServiceMessage message) throws ServiceException {
			listener.onMessage(message);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendKeepAliveMessage()
		 */
		@Override
		public void sendKeepAliveMessage() throws ServiceException {
			((LocalServiceConsumer) listener).lastMessageTime = System.currentTimeMillis();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceProducer#sendAckMessage()
		 */
		@Override
		public void sendAckMessage() throws ServiceException {
		}
	}

	private class LocalServiceConsumer implements ServiceConsumer {
		private ServiceListener listener;
		private long lastMessageTime = System.currentTimeMillis();

		/**
		 * @param listener
		 */
		public LocalServiceConsumer(final ServiceListener listener) {
			this.listener = listener;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#consumeMessages()
		 */
		@Override
		public void consumeMessages() throws ServiceException {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#dispose()
		 */
		@Override
		public void dispose() {
			listener = null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#isTimeout()
		 */
		@Override
		public boolean isTimeout() {
			return (System.currentTimeMillis() - lastMessageTime) > 120 * 1000L;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceListener#onMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
			if (listener != null) {
				lastMessageTime = System.currentTimeMillis();
				listener.onMessage(message);
			}
		}

//		/**
//		 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceConsumer#consumeMessage(com.nextbreakpoint.nextfractal.queue.network.ServiceMessage)
//		 */
//		@Override
//		public void consumeMessage(final ServiceMessage message) throws ServiceException {
//			if (listener != null) {
//				lastMessageTime = System.currentTimeMillis();
//				listener.onMessage(message);
//			}
//		}
	}

	private class CleanupHandler implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			final long pollingTime = 30 * 1000L;
			try {
				while (running) {
					try {
						synchronized (serverSessions) {
							final Iterator<LocalServiceSession> sessionIterator = serverSessions.values().iterator();
							while (sessionIterator.hasNext()) {
								final LocalServiceSession session = sessionIterator.next();
								if (session.isExpired()) {
									logger.info("Dispose expired session " + session.getSessionId());
									sessionIterator.remove();
								}
								else {
									try {
										session.consumeMessages();
									}
									catch (final Exception e) {
										e.printStackTrace();
										session.invalidate();
									}
								}
							}
						}
						synchronized (clientSessions) {
							final Iterator<LocalServiceSession> sessionIterator = clientSessions.values().iterator();
							while (sessionIterator.hasNext()) {
								final LocalServiceSession session = sessionIterator.next();
								if (session.isExpired()) {
									logger.info("Dispose expired session " + session.getSessionId());
									sessionIterator.remove();
								}
								else {
									try {
										session.consumeMessages();
									}
									catch (final Exception e) {
										e.printStackTrace();
										session.invalidate();
									}
								}
							}
						}
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
					synchronized (monitor) {
						if (!dirty) {
							monitor.wait(pollingTime);
						}
						dirty = false;
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				synchronized (serverSessions) {
					for (final ServiceSession session : serverSessions.values()) {
						logger.info("Dispose session " + session.getSessionId());
						session.dispose();
					}
					serverSessions.clear();
				}
				synchronized (clientSessions) {
					for (final ServiceSession session : clientSessions.values()) {
						logger.info("Dispose session " + session.getSessionId());
						session.dispose();
					}
					clientSessions.clear();
				}
			}
		}
	}
}
