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

import junit.framework.Assert;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.scripting.DefaultJSContext;
import com.nextbreakpoint.nextfractal.core.scripting.JSManager;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.tree.DefaultRootNode;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContextListener;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigNode;

/**
 * @author Andrea Medeghini
 */
public class TestJSManager {
	@Test
	public void test() {
		try {
			DefaultNodeSession session = new DefaultNodeSession("test");
			TwisterConfigBuilder builder = new TwisterConfigBuilder();
			TwisterConfig config = builder.createDefaultConfig();
			TwisterConfigNode configNode = new TwisterConfigNode(config);
			DefaultRootNode rootNode = new DefaultRootNode();
			rootNode.appendChildNode(configNode);
			rootNode.setSession(session);
			rootNode.setContext(new DefaultConfigContext());
			JSManager.execute(new TestRenderContext(), new TestJSContext(), rootNode, new File("."), new File("test.js"));
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	private class TestJSContext extends DefaultJSContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#loadDefaultConfig()
		 */
		@Override
		public void loadDefaultConfig() {
		}
	}

	private class TestRenderContext implements RenderContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#startRenderers()
		 */
		@Override
		public void startRenderers() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
		 */
		@Override
		public void stopRenderers() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#getImageSize()
		 */
		@Override
		public IntegerVector2D getImageSize() {
			return new IntegerVector2D(100, 100);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#refresh()
		 */
		@Override
		public void refresh() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
		 */
		@Override
		public void acquire() throws InterruptedException {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
		 */
		@Override
		public void release() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		@Override
		public void addRenderContextListener(RenderContextListener listener) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		@Override
		public void removeRenderContextListener(RenderContextListener listener) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#execute(java.lang.Runnable)
		 */
		@Override
		public void execute(Runnable task) {
		}
	}
}
