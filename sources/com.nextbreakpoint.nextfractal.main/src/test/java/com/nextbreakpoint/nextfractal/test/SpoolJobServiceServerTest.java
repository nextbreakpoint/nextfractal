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
import java.util.logging.Logger;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.queue.jxta.JXTANetworkService;
import com.nextbreakpoint.nextfractal.queue.network.spool.DistributedServiceProcessor;
import com.nextbreakpoint.nextfractal.queue.spool.DefaultDistributedJobService;
import com.nextbreakpoint.nextfractal.queue.spool.job.DistributedJob;
import com.nextbreakpoint.nextfractal.queue.spool.job.DistributedJobFactory;

/**
 * @author Andrea Medeghini
 */
public class SpoolJobServiceServerTest {
	private static final Logger logger = Logger.getLogger(SpoolJobServiceServerTest.class.getName());

	private void deleteFiles(final File path) {
		final File[] files = path.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				deleteFiles(file);
			}
			file.delete();
		}
	}

	@Test
	public void testSpool() throws Exception {
		@SuppressWarnings("unused")
		final Surface surface = new Surface(200, 200);
		final File tmpDir = new File("workdir/tmp");
		tmpDir.mkdirs();
		deleteFiles(tmpDir);
		Worker worker = new Worker(new DefaultThreadFactory("TestSpool Worker", true, Thread.MIN_PRIORITY));
		final DistributedServiceProcessor processor = new DistributedServiceProcessor(new DefaultDistributedJobService<DistributedJob>(0, "DistributedProcessor", new DistributedJobFactory(tmpDir, worker), worker), 10);
		final JXTANetworkService service = new JXTANetworkService(tmpDir, "http://nextfractal.sf.net", "JXTASpool", "Andrea Medeghini", "1.0", processor);
		processor.start();
		service.start();
		worker.start();
		try {
			while (true) {
				logger.info("Server running...");
				Thread.sleep(10000);
			}
		}
		catch (final InterruptedException e) {
		}
		worker.stop();
		service.stop();
		processor.stop();
	}
}
