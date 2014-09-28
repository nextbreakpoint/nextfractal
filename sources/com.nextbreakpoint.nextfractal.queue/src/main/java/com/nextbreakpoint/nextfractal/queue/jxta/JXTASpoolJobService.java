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

import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.queue.network.DiscoveryService;
import com.nextbreakpoint.nextfractal.queue.network.spool.AbstractSpoolJobService;
import com.nextbreakpoint.nextfractal.queue.spool.DistributedSpoolJobInterface;
import com.nextbreakpoint.nextfractal.queue.spool.JobFactory;

/**
 * @author Andrea Medeghini
 */
public class JXTASpoolJobService extends AbstractSpoolJobService {
	/**
	 * @param serviceId
	 * @param discoveryService
	 * @param jobFactory
	 * @param worker
	 */
	public JXTASpoolJobService(final int serviceId, final DiscoveryService discoveryService, final JobFactory<? extends DistributedSpoolJobInterface> jobFactory, final Worker worker) {
		super(serviceId, "JXTAProcessor", discoveryService, jobFactory, worker);
	}
}
