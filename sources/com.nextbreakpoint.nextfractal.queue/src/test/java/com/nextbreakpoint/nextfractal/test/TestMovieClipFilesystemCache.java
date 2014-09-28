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

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.util.ConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.DefaultConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClip;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipFilesystemCache;

public class TestMovieClipFilesystemCache {
	private int lastId = 1;

	@Test
	public void testCreate() {
		try {
			final RenderClipFilesystemCache fileCache = new RenderClipFilesystemCache(new File("workdir/clip"));
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			fileCache.deleteAll();
			fileCache.create(createClip().getClipId());
			fileCache.create(createClip().getClipId());
			fileCache.create(createClip().getClipId());
			fileCache.create(createClip().getClipId());
			final File[] clips = fileCache.list();
			printClips(clips);
			Assert.assertEquals(4, clips.length);
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
			final RenderClipFilesystemCache fileCache = new RenderClipFilesystemCache(new File("workdir/clip"));
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			fileCache.deleteAll();
			final RenderClipDataRow clip = createClip();
			fileCache.create(clip.getClipId());
			fileCache.delete(clip.getClipId());
			final File[] clips = fileCache.list();
			Assert.assertEquals(0, clips.length);
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
		clip.setClipId(lastId);
		clip.setClipName("Test Name");
		clip.setDescription("Test Description");
		lastId += 1;
		return clip;
	}

	private void printClips(final File[] clips) {
		for (final File clip : clips) {
			System.out.println(clip);
		}
	}
}
