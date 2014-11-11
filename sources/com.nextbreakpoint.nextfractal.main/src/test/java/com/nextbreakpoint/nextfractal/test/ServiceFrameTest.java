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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.ui.swing.util.GUIUtil;
import com.nextbreakpoint.nextfractal.core.util.ConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.DefaultConnectionFactory;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.RenderService;
import com.nextbreakpoint.nextfractal.queue.Session;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.ui.swing.RenderClipTableModel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.RenderJobTableModel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.RenderProfileTableModel;
import com.nextbreakpoint.nextfractal.twister.ui.swing.ServiceContext;
import com.nextbreakpoint.nextfractal.twister.ui.swing.ServiceFrame;

/**
 * @author Andrea Medeghini
 */
public class ServiceFrameTest {
	@Test
	public void testConfigPanel() {
		try {
			final ServiceContext context = new TestServiceContext();
			final File workspace = new File("workdir");
			synchronized (context) {
				GUIUtil.executeTask(new Runnable() {
						@Override
						public void run() {
							try {
								final ConnectionFactory factory = new DefaultConnectionFactory(workspace);
								final Session session = new Session(factory);
								ExtensionReference extensionReference = new ExtensionReference("service.spool.local", "Local Spool");
								RenderService service = new RenderService(new LibraryService(session, workspace), extensionReference);
								RenderClipTableModel renderClipTableModel = new RenderClipTableModel(service);
								RenderProfileTableModel renderProfileTableModel = new RenderProfileTableModel(service);
								RenderJobTableModel renderJobTableModel = new RenderJobTableModel(service);
								ServiceFrame frame = new ServiceFrame(context, service, renderClipTableModel, renderProfileTableModel, renderJobTableModel);
								frame.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosing(final WindowEvent e) {
										context.exit();
										super.windowClosing(e);
									}
								});
								frame.setVisible(true);
							}
							catch (Exception e) {
								Assert.fail();
								context.exit();
							}
						}
				}, true);
				context.wait();
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private class TestServiceContext implements ServiceContext {
		@Override
		public void addFrame(final JFrame frame) {
		}

		@Override
		public void removeFrame(final JFrame frame) {
		}

		@Override
		public int getFrameCount() {
			return 0;
		}

		@Override
		public void openClip(final TwisterClip clip) {
		}

		@Override
		public void exit() {
			synchronized (this) {
				notify();
			}
			System.exit(0);
		}

		@Override
		public void restart() {
			exit();
		}
	}
}
