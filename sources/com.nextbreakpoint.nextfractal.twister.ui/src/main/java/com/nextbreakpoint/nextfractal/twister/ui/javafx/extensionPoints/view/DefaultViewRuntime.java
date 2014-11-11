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

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.RenderContext;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.Extension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeSessionListener;
import com.nextbreakpoint.nextfractal.core.runtime.model.RootNode;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.DefaultView;
import com.nextbreakpoint.nextfractal.core.ui.javafx.util.EmptyView;

/**
 * @author Andrea Medeghini
 */
public class DefaultViewRuntime extends ViewExtensionRuntime {
	private static final Logger logger = Logger.getLogger(DefaultViewRuntime.class.getName());
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.view.ViewExtensionRuntime#createView(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.swing.ViewContext, com.nextbreakpoint.nextfractal.core.RenderContext)
	 */
	@Override
	public Pane createConfigView(final ExtensionConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		try {
			final Extension<NodeBuilderExtensionRuntime> extension = CoreRegistry.getInstance().getNodeBuilderExtension(config.getExtensionId());
			final NodeBuilder nodeBuilder = extension.createExtensionRuntime().createNodeBuilder(config);
			final RootNode rootNode = new RootNode("navigator.root", extension.getExtensionName() + " extension");
			nodeBuilder.createNodes(rootNode);
			rootNode.setContext(config.getContext());
			rootNode.setSession(new NavigatorNodeSession());
			return new DefaultView(viewContext, context, rootNode);
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
		return new EmptyView();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view.ViewExtensionRuntime#createEditorView(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext, com.nextbreakpoint.nextfractal.core.RenderContext, com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession)
	 */
	@Override
	public Pane createEditorView(ExtensionConfig config, ViewContext viewContext, RenderContext context, NodeSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	private class NavigatorNodeSession implements NodeSession {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#appendAction(com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction)
		 */
		@Override
		public void appendAction(final NodeAction action) {
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
			return "Navigator";
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
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#fireSessionAccepted()
		 */
		@Override
		public void fireSessionAccepted() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#fireSessionCancelled()
		 */
		@Override
		public void fireSessionCancelled() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#fireSessionChanged()
		 */
		@Override
		public void fireSessionChanged() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#addSessionListener(com.nextbreakpoint.nextfractal.core.runtime.model.NodeSessionListener)
		 */
		@Override
		public void addSessionListener(NodeSessionListener listener) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession#removeSessionListener(com.nextbreakpoint.nextfractal.core.runtime.model.NodeSessionListener)
		 */
		@Override
		public void removeSessionListener(NodeSessionListener listener) {
		}
	}
}
