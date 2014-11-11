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
package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.extensions.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.MandelbrotConfigView;
import com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.MandelbrotEditorView;
import com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view.ViewExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotImageConfigViewRuntime extends ViewExtensionRuntime {
	private static final Logger logger = Logger.getLogger(MandelbrotImageConfigViewRuntime.class.getName());
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view.ViewExtensionRuntime#createView(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext, com.nextbreakpoint.nextfractal.core.util.RenderContext, com.nextbreakpoint.nextfractal.core.tree.NodeSession)
	 */
	@Override
	public Pane createConfigView(final ExtensionConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		try {
			final Extension<NodeBuilderExtensionRuntime> extension = CoreRegistry.getInstance().getNodeBuilderExtension(config.getExtensionId());
			final NodeBuilder nodeBuilder = extension.createExtensionRuntime().createNodeBuilder(config);
			NodeObject rootNode = new RootNode("root", extension.getExtensionName());
			nodeBuilder.createNodes(rootNode);
			rootNode.setContext(config.getContext());
			rootNode.setSession(session);
			NodeObject mandelbrotFractalNode = rootNode.getChildNodeByClass(MandelbrotFractalConfigElementNode.NODE_CLASS);
			return new MandelbrotConfigView(viewContext, context, mandelbrotFractalNode);
		} catch (ExtensionException x) {
			if (MandelbrotImageConfigViewRuntime.logger.isLoggable(Level.WARNING)) {
				MandelbrotImageConfigViewRuntime.logger.log(Level.WARNING, "Can't create view for " + config.getExtensionId(), x);
			}
		}
		return null;
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view.ViewExtensionRuntime#createView(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig, com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext, com.nextbreakpoint.nextfractal.core.util.RenderContext, com.nextbreakpoint.nextfractal.core.tree.NodeSession)
	 */
	@Override
	public Pane createEditorView(final ExtensionConfig config, final ViewContext viewContext, final RenderContext context, final NodeSession session) {
		return new MandelbrotEditorView(((MandelbrotImageConfig) config).getMandelbrotConfig(), viewContext, context, session);
	}
}
