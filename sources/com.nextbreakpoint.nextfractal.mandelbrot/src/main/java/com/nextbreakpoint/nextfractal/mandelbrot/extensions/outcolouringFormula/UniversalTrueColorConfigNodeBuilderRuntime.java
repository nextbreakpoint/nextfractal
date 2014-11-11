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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.MandelbrotExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class UniversalTrueColorConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((UniversalTrueColorConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<UniversalTrueColorConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final UniversalTrueColorConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ColorRendererNode(MandelbrotExtensionResources.getInstance().getString("node.label.RedColorRendererElement"), getConfig().getColorRendererElements()[0]));
			parentNode.appendChildNode(new ColorRendererNode(MandelbrotExtensionResources.getInstance().getString("node.label.GreenColorRendererElement"), getConfig().getColorRendererElements()[1]));
			parentNode.appendChildNode(new ColorRendererNode(MandelbrotExtensionResources.getInstance().getString("node.label.BlueColorRendererElement"), getConfig().getColorRendererElements()[2]));
			parentNode.appendChildNode(new ColorRendererNode(MandelbrotExtensionResources.getInstance().getString("node.label.AlphaColorRendererElement"), getConfig().getColorRendererElements()[3]));
		}
	}

	private class ColorRendererNode extends ColorRendererConfigElementNode {
		/**
		 * @param label
		 * @param rendererElement
		 */
		public ColorRendererNode(final String label, final ColorRendererConfigElement rendererElement) {
			super(rendererElement);
			setNodeLabel(label);
		}
	}
}
