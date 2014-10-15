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
package com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view;

import java.util.List;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeSessionListener;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.tree.Tree;
import com.nextbreakpoint.nextfractal.core.ui.javafx.View;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.DefaultView;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.EmptyView;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

/**
 * @author Andrea Medeghini
 */
public class DefaultViewRuntime extends ViewExtensionRuntime {
	private static final Logger logger = Logger.getLogger(DefaultViewRuntime.class.getName());
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime#createView(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext, com.nextbreakpoint.nextfractal.core.util.RenderContext)
	 */
	@Override
	public View createView(final ExtensionConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		try {
			final Extension<NodeBuilderExtensionRuntime> extension = CoreRegistry.getInstance().getNodeBuilderExtension(config.getExtensionId());
			final NodeBuilder nodeBuilder = extension.createExtensionRuntime().createNodeBuilder(config);
			final Tree tree = new Tree(new RootNode("navigator.root", extension.getExtensionName() + " extension"));
			nodeBuilder.createNodes(tree.getRootNode());
			tree.getRootNode().setContext(config.getContext());
			tree.getRootNode().setSession(new NavigatorNodeSession());
			return new DefaultView(viewContext, context, tree.getRootNode());
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
		return new EmptyView();
	}

	private class NavigatorNodeSession implements NodeSession {
		/**
		 * 
		 */
		public NavigatorNodeSession() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#appendAction(com.nextbreakpoint.nextfractal.core.tree.NodeAction)
		 */
		@Override
		public void appendAction(final NodeAction action) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getActions()
		 */
		@Override
		public List<NodeAction> getActions() {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getSessionName()
		 */
		@Override
		public String getSessionName() {
			return "Navigator";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getTimestamp()
		 */
		@Override
		public long getTimestamp() {
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#isAcceptImmediatly()
		 */
		@Override
		public boolean isAcceptImmediatly() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#setAcceptImmediatly(boolean)
		 */
		@Override
		public void setAcceptImmediatly(final boolean isApplyImmediatly) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#setTimestamp(long)
		 */
		@Override
		public void setTimestamp(final long timestamp) {
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
