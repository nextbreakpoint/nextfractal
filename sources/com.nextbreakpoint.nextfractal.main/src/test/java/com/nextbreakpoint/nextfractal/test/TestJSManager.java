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

import junit.framework.Assert;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigNode;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.DefaultTree;
import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.scripting.DefaultJSContext;
import com.nextbreakpoint.nextfractal.core.scripting.JSManager;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContextListener;

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
			DefaultTree twisterTree = new DefaultTree();
			twisterTree.getRootNode().appendChildNode(configNode);
			twisterTree.getRootNode().setSession(session);
			twisterTree.getRootNode().setContext(new DefaultConfigContext());
			JSManager.execute(new TestRenderContext(), new TestJSContext(), twisterTree.getRootNode(), new File("."), new File("test.js"));
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
		public void loadDefaultConfig() {
		}
	}

	private class TestRenderContext implements RenderContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#startRenderers()
		 */
		public void startRenderers() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
		 */
		public void stopRenderers() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#getImageSize()
		 */
		public IntegerVector2D getImageSize() {
			return new IntegerVector2D(100, 100);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#refresh()
		 */
		public void refresh() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
		 */
		public void acquire() throws InterruptedException {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
		 */
		public void release() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		public void addRenderContextListener(RenderContextListener listener) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		public void removeRenderContextListener(RenderContextListener listener) {
		}
	}
}
