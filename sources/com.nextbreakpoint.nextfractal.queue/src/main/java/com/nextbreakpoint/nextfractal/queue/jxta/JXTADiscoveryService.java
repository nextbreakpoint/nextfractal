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
package com.nextbreakpoint.nextfractal.queue.jxta;

import java.util.LinkedList;
import java.util.List;

import com.nextbreakpoint.nextfractal.queue.network.DiscoveryService;
import com.nextbreakpoint.nextfractal.queue.network.LocalService;
import com.nextbreakpoint.nextfractal.queue.network.LocalServiceEndpoint;
import com.nextbreakpoint.nextfractal.queue.network.ServiceEndpoint;
import com.nextbreakpoint.nextfractal.queue.network.ServiceProcessor;

/**
 * @author Andrea Medeghini
 */
public class JXTADiscoveryService implements DiscoveryService {
	private final List<ServiceEndpoint> endpoints = new LinkedList<ServiceEndpoint>();
	private final JXTANetworkService networkService;
	private final LocalService localService;
	private final LocalServiceEndpoint localEndpoint;

	/**
	 * @param networkService
	 * @param processor
	 */
	public JXTADiscoveryService(final JXTANetworkService networkService, final ServiceProcessor processor) {
		this.networkService = networkService;
		localService = new LocalService("LOCAL", processor);
		localEndpoint = new LocalServiceEndpoint(localService);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.DiscoveryService#getEndpoints()
	 */
	@Override
	public List<ServiceEndpoint> getEndpoints() {
		endpoints.clear();
		for (ServiceEndpoint endpoint : networkService.getEndpoints()) {
			if (!endpoint.isInvalidated()) {
				endpoints.add(endpoint);
			}
		}
		if (!Boolean.getBoolean("excludeLocalEndpoint")) {
			endpoints.add(localEndpoint);
		}
		return endpoints;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.DiscoveryService#start()
	 */
	@Override
	public void start() {
		localService.start();
		networkService.start();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.network.DiscoveryService#stop()
	 */
	@Override
	public void stop() {
		localService.stop();
		networkService.stop();
	}
}
