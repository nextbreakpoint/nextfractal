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
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.util.ConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.DefaultConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClip;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfile;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataTable;

public class TestRenderProfileDataTable {
	@Test
	public void testCreate() {
		try {
			final ConnectionFactory factory = new DefaultConnectionFactory(new File("workdir"));
			final Session session = new Session(factory);
			session.openTransaction();
			final RenderProfileDataTable dataTable = new RenderProfileDataTable();
			dataTable.deleteAll(session);
			dataTable.create(session, createProfile());
			dataTable.create(session, createProfile());
			dataTable.create(session, createProfile());
			dataTable.create(session, createProfile());
			final List<RenderProfileDataRow> profiles = dataTable.loadAll(session);
			printProfiles(profiles);
			Assert.assertEquals(4, profiles.size());
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
			final RenderProfileDataTable dataTable = new RenderProfileDataTable();
			dataTable.deleteAll(session);
			RenderProfileDataRow profile = createProfile();
			dataTable.create(session, profile);
			profile.setProfileName("Name");
			profile.setImageWidth(640);
			profile.setImageHeight(480);
			dataTable.save(session, profile);
			profile = dataTable.load(session, profile.getProfileId());
			System.out.println(profile);
			Assert.assertEquals("Name", profile.getProfileName());
			Assert.assertEquals(640, profile.getImageWidth());
			Assert.assertEquals(480, profile.getImageHeight());
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
			final RenderProfileDataTable dataTable = new RenderProfileDataTable();
			dataTable.deleteAll(session);
			final RenderProfileDataRow profile = createProfile();
			dataTable.create(session, profile);
			dataTable.delete(session, profile);
			final List<RenderProfileDataRow> profiles = dataTable.loadAll(session);
			Assert.assertEquals(0, profiles.size());
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
			final RenderProfileDataTable dataTable = new RenderProfileDataTable();
			dataTable.deleteAll(session);
			final RenderProfileDataRow profile = createProfile();
			for (int i = 0; i < 4; i++) {
				dataTable.create(session, profile);
				profile.setProfileName("Name" + i);
				dataTable.save(session, profile);
			}
			final RenderClipDataRow clip = new RenderClipDataRow(new RenderClip());
			clip.setClipId(1);
			profile.setProfileName("%Na%");
			List<RenderProfileDataRow> profiles = dataTable.load(session, clip.getClipId(), profile);
			printProfiles(profiles);
			Assert.assertEquals(4, profiles.size());
			profile.setProfileName("%1");
			profiles = dataTable.load(session, clip.getClipId(), profile);
			printProfiles(profiles);
			Assert.assertEquals(1, profiles.size());
			session.closeTransaction();
			session.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	private RenderProfileDataRow createProfile() {
		final RenderProfileDataRow profile = new RenderProfileDataRow(new RenderProfile());
		profile.setProfileName("Test Name");
		profile.setClipId(1);
		profile.setImageWidth(320);
		profile.setImageHeight(200);
		profile.setFrameRate(0);
		profile.setStartTime(0);
		profile.setStopTime(0);
		profile.setQuality(100);
		return profile;
	}

	private void printProfiles(final List<RenderProfileDataRow> profiles) {
		for (final RenderProfileDataRow profile : profiles) {
			System.out.println(profile);
		}
	}
}
