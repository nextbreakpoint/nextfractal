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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.PaletteRendererConfigElementNode;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractOutcolouringPaletteConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((AbstractOutcolouringPaletteConfig) config);
	}

	/**
	 * @param parentNode
	 * @param config
	 */
	public void createNodes(final Node parentNode, final AbstractOutcolouringPaletteConfig config) {
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<AbstractOutcolouringPaletteConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final AbstractOutcolouringPaletteConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new PaletteRendererConfigElementNode(getConfig().getPaletteRendererElement()));
			AbstractOutcolouringPaletteConfigNodeBuilderRuntime.this.createNodes(parentNode, getConfig());
		}
	}
}
