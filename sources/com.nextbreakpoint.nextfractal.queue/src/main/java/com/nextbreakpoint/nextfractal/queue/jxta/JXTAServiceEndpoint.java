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
package com.nextbreakpoint.nextfractal.queue.jxta;

import com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.queue.network.ServiceException;
import com.nextbreakpoint.nextfractal.queue.network.ServiceListener;
import com.nextbreakpoint.nextfractal.queue.network.ServiceSession;

import net.jxta.protocol.PipeAdvertisement;

/**
 * @author Andrea Medeghini
 */
public class JXTAServiceEndpoint implements ServiceEndpoint {
	private final JXTANetworkService networkService;
	private final String serviceName;
	private final PipeAdvertisement pipeadv;
	private volatile int jobCount;
	private volatile long lastUpdate;

	/**
	 * @param networkService
	 * @param pipeadv
	 */
	public JXTAServiceEndpoint(final JXTANetworkService networkService, final PipeAdvertisement pipeadv) {
		serviceName = "RemoteService (pipeID = " + pipeadv.getPipeID() + ")";
		this.networkService = networkService;
		this.pipeadv = pipeadv;
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
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#createSession(com.nextbreakpoint.nextfractal.queue.network.ServiceListener)
	 */
	public ServiceSession createSession(final ServiceListener listener) throws ServiceException {
		ServiceSession session = networkService.createSession(pipeadv, listener);
		session.setEndpoint(this);
		return session;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#invalidate()
	 */
	public void invalidate() {
		networkService.invalidate(pipeadv);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#isInvalidated()
	 */
	public boolean isInvalidated() {
		return networkService.isInvalidated(pipeadv);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#setJobCount(int)
	 */
	public void setJobCount(final int jobCount) {
		this.jobCount = jobCount;
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#getJobCount()
	 */
	public int getJobCount() {
		return jobCount;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint#getJobCountLastUpdate()
	 */
	public long getJobCountLastUpdate() {
		return lastUpdate;
	}
}
