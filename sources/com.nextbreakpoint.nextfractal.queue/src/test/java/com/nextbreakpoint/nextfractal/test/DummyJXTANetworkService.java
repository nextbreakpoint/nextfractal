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
import java.util.List;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.queue.jxta.JXTADiscoveryService;
import com.nextbreakpoint.nextfractal.queue.jxta.JXTANetworkService;
import com.nextbreakpoint.nextfractal.queue.network.RequestIDFactory;
import com.nextbreakpoint.nextfractal.queue.network.RequestMessage;
import com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.queue.network.ServiceException;
import com.nextbreakpoint.nextfractal.queue.network.ServiceListener;
import com.nextbreakpoint.nextfractal.queue.network.ServiceMessage;
import com.nextbreakpoint.nextfractal.queue.network.ServiceProcessor;
import com.nextbreakpoint.nextfractal.queue.network.ServiceSession;
import com.nextbreakpoint.nextfractal.queue.network.SessionHandler;

/**
 * @author Andrea Medeghini
 */
public class DummyJXTANetworkService {
	private static final Logger logger = Logger.getLogger(DummyJXTANetworkService.class.getName());

	/**
	 * @param args
	 */
	public static void main(final String args[]) {
		final File tmpDir = new File("workdir/tmp");
		final JXTADiscoveryService service = new JXTADiscoveryService(new JXTANetworkService(tmpDir, "http://nextfractal.sf.net", "JXTASpool", "Andrea Medeghini", "1.0", new TestProcessor()), new TestProcessor());
		service.start();
		try {
			Thread.sleep(10000);
			while (true) {
				final List<ServiceEndpoint> endpoints = service.getEndpoints();
				for (final ServiceEndpoint endpoint : endpoints) {
					final ServiceSession[] sessions = new ServiceSession[5];
					for (int i = 0; i < 5; i++) {
						try {
							sessions[i] = endpoint.createSession(new TestListener());
							final RequestMessage request = new RequestMessage();
							request.setRequestId(RequestIDFactory.newRequestId());
							request.setRequestType(0);
							request.setUserData(new Integer(i));
							sessions[i].sendMessage(request);
						}
						catch (final Exception e) {
							e.printStackTrace();
						}
						Thread.sleep(1000);
					}
					Thread.sleep(30000);
					for (int i = 0; i < 5; i++) {
						try {
							if (sessions[i] != null) {
								sessions[i].dispose();
							}
						}
						catch (final Exception e) {
							e.printStackTrace();
						}
					}
					Thread.sleep(180000);
				}
			}
		}
		catch (final InterruptedException e) {
		}
		service.stop();
	}

	private static class TestProcessor implements ServiceProcessor {
		@Override
		public void start() {
		}

		@Override
		public void stop() {
		}

		@Override
		public SessionHandler createSessionHandler() {
			return null;
		}
	}

	private static class TestListener implements ServiceListener {
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
			switch (message.getMessageType()) {
				case ServiceMessage.MESSAGE_TYPE_REQUEST: {
					break;
				}
				case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
					logger.fine(message.toString());
					break;
				}
				case ServiceMessage.MESSAGE_TYPE_EVENT: {
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
