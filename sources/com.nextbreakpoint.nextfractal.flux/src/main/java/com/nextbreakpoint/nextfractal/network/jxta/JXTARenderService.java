package com.nextbreakpoint.nextfractal.network.jxta;

import java.io.File;
import java.util.Collection;

import com.nextbreakpoint.nextfractal.ExportJob;
import com.nextbreakpoint.nextfractal.RenderService;
import com.nextbreakpoint.nextfractal.network.core.RequestMessage;
import com.nextbreakpoint.nextfractal.network.core.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.network.core.ServiceException;
import com.nextbreakpoint.nextfractal.network.core.ServiceMessage;
import com.nextbreakpoint.nextfractal.network.core.ServiceSession;
import com.nextbreakpoint.nextfractal.network.core.SessionDelegate;

public class JXTARenderService implements RenderService {
	private final JXTANetworkService networkService;
	
	/**
	 * @param workDir
	 * @param renderService
	 */
	public JXTARenderService(File workDir, RenderService renderService) {
		this.networkService = new JXTANetworkService(workDir, "http://nextbreakpoint.com", "JXTARenderService", "NextBreakpoint", "1.0", new JXTAServiceProcessor(renderService));
		networkService.start();
	}
	
	@Override
	public void dispatch(ExportJob job) {
		Collection<ServiceEndpoint> endpoints = networkService.getEndpoints();
		for (ServiceEndpoint endpoint : endpoints) {
			try {
				ServiceSession session = endpoint.createSession(new JXTASessionDelegate());
				if (session != null) {
					job.setDispatched(true);
					dispatchJob(session, job);
					break;
				}
			} catch (ServiceException e) {
			}
		}
	}
	
	private void dispatchJob(ServiceSession session, ExportJob job) {
		try {
			RequestMessage helloMessage = createHelloRequest();
			session.sendMessage(helloMessage);
		} catch (ServiceException e) {
		}
	}

	private RequestMessage createHelloRequest() {
		final RequestMessage message = new RequestMessage();
		message.setRequestType(RequestMessage.TYPE_HELLO);
		return message;
	}

	private class JXTASessionDelegate implements SessionDelegate {
		@Override
		public void onMessage(ServiceMessage message) throws ServiceException {
		}
	}
}
