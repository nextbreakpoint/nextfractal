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
package com.nextbreakpoint.nextfractal.queue.spool.job;

import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.spool.JobFactory;
import com.nextbreakpoint.nextfractal.queue.spool.JobListener;

/**
 * @author Andrea Medeghini
 */
public class LocalSpoolJobFactory implements JobFactory<LocalSpoolJob> {
	private final LibraryService service;
	private final Worker worker;

	/**
	 * @param service
	 */
	public LocalSpoolJobFactory(final LibraryService service, final Worker worker) {
		this.service = service;
		this.worker = worker;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobFactory#createJob(java.lang.String, com.nextbreakpoint.nextfractal.queue.spool.JobListener)
	 */
	@Override
	public LocalSpoolJob createJob(final String jobId, final JobListener listener) {
		return new LocalSpoolJob(service, worker, jobId, listener);
	}
}
