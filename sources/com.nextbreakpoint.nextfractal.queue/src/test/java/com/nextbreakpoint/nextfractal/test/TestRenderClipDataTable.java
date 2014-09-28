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
import com.nextbreakpoint.nextfractal.queue.clip.RenderClip;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataTable;

public class TestRenderClipDataTable {
	@Test
	public void testCreate() {
		try {
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			final RenderClipDataTable dataTable = new RenderClipDataTable();
			dataTable.deleteAll(session);
			dataTable.create(session, createClip());
			dataTable.create(session, createClip());
			dataTable.create(session, createClip());
			dataTable.create(session, createClip());
			final List<RenderClipDataRow> clips = dataTable.loadAll(session);
			printClips(clips);
			Assert.assertEquals(4, clips.size());
			session.closeTransaction();
			session.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testSave() {
		try {
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			final RenderClipDataTable dataTable = new RenderClipDataTable();
			dataTable.deleteAll(session);
			RenderClipDataRow clip = createClip();
			dataTable.create(session, clip);
			clip.setClipName("Name");
			clip.setDescription("Description");
			dataTable.save(session, clip);
			clip = dataTable.load(session, clip.getClipId());
			System.out.println(clip);
			Assert.assertEquals("Name", clip.getClipName());
			Assert.assertEquals("Description", clip.getDescription());
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
			final RenderClipDataTable dataTable = new RenderClipDataTable();
			dataTable.deleteAll(session);
			final RenderClipDataRow clip = createClip();
			dataTable.create(session, clip);
			dataTable.delete(session, clip);
			final List<RenderClipDataRow> clips = dataTable.loadAll(session);
			Assert.assertEquals(0, clips.size());
			session.closeTransaction();
			session.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testLoad() {
		try {
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			final RenderClipDataTable dataTable = new RenderClipDataTable();
			dataTable.deleteAll(session);
			final RenderClipDataRow clip = createClip();
			for (int i = 0; i < 4; i++) {
				dataTable.create(session, clip);
				clip.setClipName("Name" + i);
				clip.setDescription("Description" + i);
				dataTable.save(session, clip);
			}
			clip.setClipName("%Na%");
			clip.setDescription("%");
			List<RenderClipDataRow> clips = dataTable.load(session, clip);
			printClips(clips);
			Assert.assertEquals(4, clips.size());
			clip.setClipName("%1");
			clip.setDescription("%1");
			clips = dataTable.load(session, clip);
			printClips(clips);
			Assert.assertEquals(1, clips.size());
			session.closeTransaction();
			session.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	private RenderClipDataRow createClip() {
		final RenderClipDataRow clip = new RenderClipDataRow(new RenderClip());
		clip.setClipName("Test Name");
		clip.setDescription("Test Description");
		return clip;
	}

	private void printClips(final List<RenderClipDataRow> clips) {
		for (final RenderClipDataRow clip : clips) {
			System.out.println(clip);
		}
	}
}
