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
package com.nextbreakpoint.nextfractal.spool.impl;

import java.io.File;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.net.jxta.JXTADiscoveryService;
import com.nextbreakpoint.nextfractal.net.jxta.JXTANetworkService;
import com.nextbreakpoint.nextfractal.spool.JobInterface;
import com.nextbreakpoint.nextfractal.spool.JobService;
import com.nextbreakpoint.nextfractal.spool.job.LocalJob;
import com.nextbreakpoint.nextfractal.spool.job.LocalJobFactory;
import com.nextbreakpoint.nextfractal.spool.job.RemoteJob;
import com.nextbreakpoint.nextfractal.spool.job.RemoteJobFactory;
import com.nextbreakpoint.nextfractal.spool.job.SpoolJobFactory;
import com.nextbreakpoint.nextfractal.spool.jobservice.DefaultJobService;
import com.nextbreakpoint.nextfractal.spool.jobservice.JXTAJobService;
import com.nextbreakpoint.nextfractal.spool.jobservice.RemoteJobService;
import com.nextbreakpoint.nextfractal.spool.processor.LocalServiceProcessor;
import com.nextbreakpoint.nextfractal.spool.processor.RemoteServiceProcessor;

/**
 * @author Andrea Medeghini
 */
public class JXTASpool {
	public JobService<? extends JobInterface> getJobService(final int serviceId, final Worker worker) {
		File tmpDir = new File("tmp");//TODO tmp
		final RemoteServiceProcessor processor1 = new RemoteServiceProcessor(new RemoteJobService<RemoteJob>(serviceId, "DistributedProcessor", new RemoteJobFactory(new File(tmpDir , "spool"), worker), worker), 10);
		final LocalServiceProcessor processor2 = new LocalServiceProcessor(new DefaultJobService<LocalJob>(serviceId, "LocalProcessor", new LocalJobFactory(worker), worker), 10);
		final JXTAJobService jobService = new JXTAJobService(serviceId, "JXTAProcessor", new JXTADiscoveryService(new JXTANetworkService(tmpDir, "http://nextfractal.sf.net", "JXTASpool", "Andrea Medeghini", "2.0.0", processor1), processor2), new SpoolJobFactory(worker), worker);
		return jobService;
	}
}
