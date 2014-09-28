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
package com.nextbreakpoint.nextfractal.test;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.util.ConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.DefaultConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.queue.job.RenderJob;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobService;

public class TestRenderJobService {
	@Test
	public void testCreate() {
		try {
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			final RenderJobService service = new RenderJobService(new File("workdir"));
			service.deleteAll(session);
			service.create(session, createJob());
			service.create(session, createJob());
			service.create(session, createJob());
			service.create(session, createJob());
			final List<RenderJobDataRow> jobs = service.loadAll(session);
			printJobs(jobs);
			Assert.assertEquals(4, jobs.size());
			session.closeTransaction();
			session.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testDelete() {
		try {
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			final RenderJobService service = new RenderJobService(new File("workdir"));
			service.deleteAll(session);
			final RenderJobDataRow job = createJob();
			service.create(session, job);
			service.delete(session, job);
			final List<RenderJobDataRow> jobs = service.loadAll(session);
			Assert.assertEquals(0, jobs.size());
			session.closeTransaction();
			session.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	private RenderJobDataRow createJob() {
		final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
		job.setProfileName("Test Name");
		return job;
	}

	private void printJobs(final List<RenderJobDataRow> jobs) {
		for (final RenderJobDataRow job : jobs) {
			System.out.println(job);
		}
	}
}
