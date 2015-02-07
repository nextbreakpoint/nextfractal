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

import java.io.File;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.net.jxta.JXTADiscoveryService;
import com.nextbreakpoint.nextfractal.net.jxta.JXTANetworkService;
import com.nextbreakpoint.nextfractal.spool.factory.DistributedJobFactory;
import com.nextbreakpoint.nextfractal.spool.factory.DistributedSpoolJobFactory;
import com.nextbreakpoint.nextfractal.spool.factory.LocalSpoolJobFactory;
import com.nextbreakpoint.nextfractal.spool.job.DistributedJob;
import com.nextbreakpoint.nextfractal.spool.job.LocalSpoolJob;
import com.nextbreakpoint.nextfractal.spool.processor.DistributedServiceProcessor;
import com.nextbreakpoint.nextfractal.spool.processor.LocalServiceProcessor;

/**
 * @author Andrea Medeghini
 */
public class JXTASpoolRuntime {
	public JobService<? extends JobInterface> getJobService(final int serviceId, final LibraryService service, final Worker worker) {
		final File tmpDir = new File(service.getWorkspace(), "JXTASpool");
		final DistributedServiceProcessor processor1 = new DistributedServiceProcessor(new DefaultDistributedJobService<DistributedJob>(serviceId, "DistributedProcessor", new DistributedJobFactory(new File(tmpDir, "spool"), worker), worker), 10);
		final LocalServiceProcessor processor2 = new LocalServiceProcessor(new DefaultJobService<LocalSpoolJob>(serviceId, "LocalProcessor", new LocalSpoolJobFactory(service, worker), worker), 10);
		final JXTASpoolJobService jobService = new JXTASpoolJobService(serviceId, new JXTADiscoveryService(new JXTANetworkService(tmpDir, "http://nextfractal.sf.net", "JXTASpool", "Andrea Medeghini", "2.0.0", processor1), processor2), new DistributedSpoolJobFactory(service, worker), worker);
		return jobService;
	}
}
