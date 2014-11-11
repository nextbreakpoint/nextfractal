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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap;

import com.nextbreakpoint.nextfractal.core.common.DoubleElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.MandelbrotExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class CircleTrapConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((CircleTrapConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<CircleTrapConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final CircleTrapConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ThresholdNode(getConfig()));
			parentNode.appendChildNode(new SizeNode(getConfig()));
			parentNode.appendChildNode(new CriteriaNode(getConfig()));
		}

		private class SizeNode extends DoubleElementNode {
			private final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.SizeElement");

			/**
			 * @param config
			 * @param index
			 */
			public SizeNode(final CircleTrapConfig config) {
				super(config.getExtensionId() + ".size", config.getSizeElement());
				setNodeLabel(NODE_LABEL);
			}
		}

		private class ThresholdNode extends DoubleElementNode {
			private final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.ThresholdElement");

			/**
			 * @param config
			 * @param index
			 */
			public ThresholdNode(final CircleTrapConfig config) {
				super(config.getExtensionId() + ".threshold", config.getThresholdElement());
				setNodeLabel(NODE_LABEL);
			}
		}

		private class CriteriaNode extends CriteriaElementNode {
			private final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.CriteriaElement");

			/**
			 * @param config
			 * @param index
			 */
			public CriteriaNode(final CircleTrapConfig config) {
				super(config.getExtensionId() + ".criteria", config.getCriteriaElement());
				setNodeLabel(NODE_LABEL);
			}
		}
	}
}
