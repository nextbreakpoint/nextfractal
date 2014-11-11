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
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.RenderContext;
import com.nextbreakpoint.nextfractal.core.RenderContextListener;
import com.nextbreakpoint.nextfractal.core.launcher.Launcher;
import com.nextbreakpoint.nextfractal.core.launcher.LauncherContextListener;
import com.nextbreakpoint.nextfractal.core.launcher.LauncherThreadFactory;
import com.nextbreakpoint.nextfractal.core.runtime.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.runtime.model.DefaultRootNode;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeSessionListener;
import com.nextbreakpoint.nextfractal.core.runtime.scripting.DefaultJSContext;
import com.nextbreakpoint.nextfractal.core.runtime.scripting.JSException;
import com.nextbreakpoint.nextfractal.core.runtime.scripting.JSManager;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.ui.swing.ConfigFrame;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterConfigContext;
import com.nextbreakpoint.nextfractal.twister.ui.swing.TwisterContext;

/**
 * @author Andrea Medeghini
 */
public class ConfigFrameTest {
	private final Launcher<TwisterContext> launcher = new Launcher<TwisterContext>(new TestTwisterContext(), new TestThreadFactory());
	private DefaultRootNode rootNode;
	private TwisterConfig config;

	@Test
	public void testConfigPanel() {
		try {
			launcher.init();
			launcher.start();
			launcher.dispatch();
			launcher.dispose();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private class TestThreadFactory implements LauncherThreadFactory<TwisterContext> {
		@Override
		public Thread createThread(final TwisterContext context) {
			final Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						try {
							UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						}
						catch (Exception x) {
							x.printStackTrace();
						}
						final TestRenderContext renderContext = new TestRenderContext();
						final NodeSession session = new TestNodeSesion();
						rootNode = new DefaultRootNode();
						TwisterConfigBuilder builder = new TwisterConfigBuilder();
						config = builder.createDefaultConfig();
						config.setContext(new DefaultConfigContext());
						final TwisterConfigNodeBuilder nodeBuilder = new TwisterConfigNodeBuilder(config);
						rootNode.setContext(config.getContext());
						rootNode.setSession(session);
						nodeBuilder.createNodes(rootNode);
						final ConfigFrame frame = new ConfigFrame(new TestTwisterConfigContext(renderContext), config, renderContext, session);
						frame.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(final WindowEvent e) {
								context.exit();
							}
						});
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								frame.setVisible(true);
							}
						});
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								frame.setup();
							}
						});
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return thread;
		}
	}

	private class TestTwisterConfigContext implements TwisterConfigContext {
		private RenderContext renderContext;

		public TestTwisterConfigContext(TestRenderContext renderContext) {
			this.renderContext = renderContext;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterConfigContext#openAdvancedConfigWindow()
		 */
		@Override
		public void openAdvancedConfigWindow() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterConfigContext#executeScript(java.io.File)
		 */
		public void executeScript(File scriptFile) throws JSException {
			JSManager.execute(renderContext, new TestJSContext(), rootNode, scriptFile.getParentFile(), scriptFile);
		}
	}

	private class TestJSContext extends DefaultJSContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSContext#loadDefaultConfig()
		 */
		@Override
		public void loadDefaultConfig() {
		}
	}

	private class TestTwisterContext implements TwisterContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterContext#addFrame(javax.swing.JFrame)
		 */
		@Override
		public void addFrame(final JFrame frame) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterContext#exit()
		 */
		@Override
		public void exit() {
			launcher.stop();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterContext#getFrameCount()
		 */
		@Override
		public int getFrameCount() {
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterContext#removeFrame(javax.swing.JFrame)
		 */
		@Override
		public void removeFrame(final JFrame frame) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.swing.TwisterContext#restart()
		 */
		@Override
		public void restart() {
			launcher.stop();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.launcher.LauncherContext#setContextListener(com.nextbreakpoint.nextfractal.launcher.LauncherContextListener)
		 */
		@Override
		public void setContextListener(final LauncherContextListener listener) {
		}
	}

	private class TestRenderContext implements RenderContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#startRenderers()
		 */
		@Override
		public void startRenderers() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#stopRenderers()
		 */
		@Override
		public void stopRenderers() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#getImageSize()
		 */
		@Override
		public IntegerVector2D getImageSize() {
			return new IntegerVector2D(100, 100);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#refresh()
		 */
		@Override
		public void refresh() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#acquire()
		 */
		@Override
		public void acquire() throws InterruptedException {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#release()
		 */
		@Override
		public void release() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.RenderContextListener)
		 */
		@Override
		public void addRenderContextListener(RenderContextListener listener) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.RenderContextListener)
		 */
		@Override
		public void removeRenderContextListener(RenderContextListener listener) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.RenderContext#execute(java.lang.Runnable)
		 */
		@Override
		public void execute(Runnable task) {
		}
	}

	private class TestNodeSesion implements NodeSession {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#appendAction(com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction)
		 */
		@Override
		public void appendAction(final NodeAction action) {
			System.out.println(action);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#getActions()
		 */
		@Override
		public List<NodeAction> getActions() {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#getSessionName()
		 */
		@Override
		public String getSessionName() {
			return "Test";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#getTimestamp()
		 */
		@Override
		public long getTimestamp() {
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#isAcceptImmediatly()
		 */
		@Override
		public boolean isAcceptImmediatly() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#setAcceptImmediatly(boolean)
		 */
		@Override
		public void setAcceptImmediatly(final boolean isApplyImmediatly) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#setTimestamp(long)
		 */
		@Override
		public void setTimestamp(final long timestamp) {
			System.out.println("Timestamp = " + timestamp);
		}

		@Override
		public void fireSessionAccepted() {
		}

		@Override
		public void fireSessionCancelled() {
		}

		@Override
		public void fireSessionChanged() {
		}

		@Override
		public void addSessionListener(NodeSessionListener listener) {
		}

		@Override
		public void removeSessionListener(NodeSessionListener listener) {
		}
	}
}
