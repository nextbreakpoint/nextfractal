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

import com.nextbreakpoint.nextfractal.core.elements.DoubleElementNode;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.CriteriaElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.MandelbrotExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class LineTrapConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((LineTrapConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<LineTrapConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final LineTrapConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ThresholdNode(getConfig()));
			parentNode.appendChildNode(new RotationNode(getConfig()));
			parentNode.appendChildNode(new LengthNode(getConfig()));
			parentNode.appendChildNode(new CriteriaNode(getConfig()));
		}

		private class LengthNode extends DoubleElementNode {
			private final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.LengthElement");

			/**
			 * @param config
			 * @param index
			 */
			public LengthNode(final LineTrapConfig config) {
				super(config.getExtensionId() + ".length", config.getLengthElement());
				setNodeLabel(NODE_LABEL);
			}
		}

		private class RotationNode extends DoubleElementNode {
			private final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.RotationElement");

			/**
			 * @param config
			 * @param index
			 */
			public RotationNode(final LineTrapConfig config) {
				super(config.getExtensionId() + ".rotation", config.getRotationElement());
				setNodeLabel(NODE_LABEL);
			}
		}

		private class ThresholdNode extends DoubleElementNode {
			private final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.ThresholdElement");

			/**
			 * @param config
			 * @param index
			 */
			public ThresholdNode(final LineTrapConfig config) {
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
			public CriteriaNode(final LineTrapConfig config) {
				super(config.getExtensionId() + ".criteria", config.getCriteriaElement());
				setNodeLabel(NODE_LABEL);
			}
		}
	}
}
