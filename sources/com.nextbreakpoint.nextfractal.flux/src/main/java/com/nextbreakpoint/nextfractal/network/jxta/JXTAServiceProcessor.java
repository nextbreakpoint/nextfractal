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
package com.nextbreakpoint.nextfractal.network.jxta;

import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.JobDecoder;
import com.nextbreakpoint.nextfractal.RenderService;
import com.nextbreakpoint.nextfractal.network.core.RequestMessage;
import com.nextbreakpoint.nextfractal.network.core.ResponseMessage;
import com.nextbreakpoint.nextfractal.network.core.ServiceException;
import com.nextbreakpoint.nextfractal.network.core.ServiceMessage;
import com.nextbreakpoint.nextfractal.network.core.ServiceProcessor;
import com.nextbreakpoint.nextfractal.network.core.ServiceSession;
import com.nextbreakpoint.nextfractal.network.core.SessionHandler;

/**
 * @author Andrea Medeghini
 */
public class JXTAServiceProcessor implements ServiceProcessor {
	private static final Logger logger = Logger.getLogger(JXTAServiceProcessor.class.getName());

	public JXTAServiceProcessor(RenderService renderService) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.network.core.queue.network.ServiceProcessor#createSessionHandler()
	 */
	@Override
	public SessionHandler createSessionHandler() {
		return new JXTASessionHandler();
	}

	private class JXTASessionHandler implements SessionHandler {
		private ServiceSession session;

		/**
		 * @see com.nextbreakpoint.nextfractal.network.core.SessionHandler#dispose()
		 */
		@Override
		public void dispose() {
			if (session != null) {
				session.dispose();
				session = null;
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.network.core.SessionHandler#setSession(com.nextbreakpoint.nextfractal.network.core.ServiceSession)
		 */
		@Override
		public void setSession(final ServiceSession session) {
			if (session == null) {
				throw new IllegalArgumentException("session == null");
			}
			this.session = session;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.network.core.SessionHandler#getSession()
		 */
		@Override
		public ServiceSession getSession() {
			return session;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.network.core.SessionHandler#isExpired()
		 */
		@Override
		public boolean isExpired() {
			return (session == null) || session.isExpired();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.network.core.SessionDelegate#onMessage(com.nextbreakpoint.nextfractal.network.core.ServiceMessage)
		 */
		@Override
		public void onMessage(final ServiceMessage message) throws ServiceException {
			if (isExpired()) {
				return;
			}
			try {
				switch (message.getMessageType()) {
					case ServiceMessage.MESSAGE_TYPE_REQUEST: {
						final RequestMessage request = (RequestMessage) message;
						switch (request.getRequestType()) {
							case RequestMessage.TYPE_HELLO: {
								processHelloRequest(request);
								break;
							}
							case RequestMessage.TYPE_PUT: {
								processPutRequest(request);
								break;
							}
							case RequestMessage.TYPE_GET: {
								processGetRequest(request);
								break;
							}
							case RequestMessage.TYPE_ABORT: {
								processAbortRequest(request);
								break;
							}
							case RequestMessage.TYPE_DELETE: {
								processDeleteRequest(request);
								break;
							}
							default: {
								break;
							}
						}
						break;
					}
					case ServiceMessage.MESSAGE_TYPE_RESPONSE: {
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
			catch (final Exception e) {
				e.printStackTrace();
			}
		}

		private void processDeleteRequest(final RequestMessage request) throws Exception {
//			final String jobId = (String) request.getUserData();
//			jobService.deleteJob(jobId);
//			final ResponseMessage response = createDeleteResponse(request, jobId);
		}

		private void processAbortRequest(final RequestMessage request) throws Exception {
//			final String jobId = (String) request.getUserData();
//			jobService.abortJob(jobId);
//			jobService.stopJob(jobId);
//			final ResponseMessage response = createAbortResponse(request, jobId);
		}

		private void processGetRequest(final RequestMessage request) throws Exception {
//			final String jobId = (String) ((Object[]) request.getUserData())[0];
//			final int frameNumber = (Integer) ((Object[]) request.getUserData())[1];
//			byte[] frameData = jobService.getJobFrame(jobId, frameNumber);
//			final ResponseMessage response = createGetResponse(request, jobId, frameNumber, frameData);
		}

		private void processPutRequest(final RequestMessage request) throws Exception {
//			final String jobId = (String) ((Object[]) request.getUserData())[0];
//			final int frameNumber = (Integer) ((Object[]) request.getUserData())[1];
//			final byte[] data = (byte[]) ((Object[]) request.getUserData())[2];
//			final JobDecoder decoder = new JobDecoder(data);
//			jobService.setJobData(jobId, decoder.getProfile(), frameNumber);
//			jobService.setJobFrame(jobId, decoder.getFrameData());
//			jobService.runJob(jobId);
//			final ResponseMessage response = createPutResponse(request, jobId);
		}

		private void processHelloRequest(final RequestMessage request) throws Exception {
//			final ResponseMessage response = createHelloResponse(request, jobCount, jobId);
		}

		private ResponseMessage createHelloResponse(final RequestMessage request, final Integer jobCount, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_HELLO);
			if (jobId != null) {
				message.setUserData(new Object[] { jobCount, jobId });
				message.setReturnCode(0);
			}
			else {
				message.setUserData(new Object[] { jobCount, null });
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createPutResponse(final RequestMessage request, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_PUT);
			if (jobId != null) {
				message.setUserData(jobId);
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createGetResponse(final RequestMessage request, final String jobId, final int frameNumber, final byte[] frameData) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_GET);
			if ((jobId != null) && (frameData != null)) {
				message.setUserData(new Object[] { jobId, frameNumber, frameData });
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createAbortResponse(final RequestMessage request, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_ABORT);
			if (jobId != null) {
				message.setUserData(jobId);
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}

		private ResponseMessage createDeleteResponse(final RequestMessage request, final String jobId) throws Exception {
			final ResponseMessage message = new ResponseMessage();
			message.setRequestId(request.getRequestId());
			message.setResponseType(RequestMessage.TYPE_DELETE);
			if (jobId != null) {
				message.setUserData(jobId);
				message.setReturnCode(0);
			}
			else {
				message.setReturnCode(1);
			}
			return message;
		}
	}
}
