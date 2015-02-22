package com.nextbreakpoint.nextfractal.service;

import java.io.File;
import java.util.Collection;

import com.nextbreakpoint.nextfractal.ExportJob;
import com.nextbreakpoint.nextfractal.RenderService;
import com.nextbreakpoint.nextfractal.network.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.network.ServiceException;
import com.nextbreakpoint.nextfractal.network.ServiceMessage;
import com.nextbreakpoint.nextfractal.network.ServiceSession;
import com.nextbreakpoint.nextfractal.network.SessionDelegate;
import com.nextbreakpoint.nextfractal.network.jxta.JXTANetworkService;
import com.nextbreakpoint.nextfractal.network.jxta.JXTAServiceProcessor;

public class JXTARenderService implements RenderService {
	private final JXTANetworkService networkService;
	
	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param networkService
	 */
	public JXTARenderService(RenderService renderService) {
		this.networkService = new JXTANetworkService(new File("jxta"), "http://nextbreakpoint.com", "JXTARenderService", "NextBreakpoint", "1.0", new JXTAServiceProcessor(renderService));
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
		// TODO Auto-generated method stub
		
	}

	private class JXTASessionDelegate implements SessionDelegate {
		@Override
		public void onMessage(ServiceMessage message) throws ServiceException {
		}
	}
}
