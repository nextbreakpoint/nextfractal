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
package com.nextbreakpoint.nextfractal.twister.extensions.effect;

import com.nextbreakpoint.nextfractal.core.elements.ComplexElementNode;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.elements.PercentageElementNode;
import com.nextbreakpoint.nextfractal.twister.extensions.TwisterExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class SpotConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SpotConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SpotConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SpotConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new IntensityNode(getConfig()));
			parentNode.appendChildNode(new CenterNode(getConfig()));
		}

		private class IntensityNode extends PercentageElementNode {
			private final String NODE_LABEL = TwisterExtensionResources.getInstance().getString("node.label.IntensityElement");

			/**
			 * @param config
			 */
			public IntensityNode(final SpotConfig config) {
				super(config.getExtensionId() + ".intensity", config.getIntensityElement());
				setNodeLabel(NODE_LABEL);
			}
		}

		private class CenterNode extends ComplexElementNode {
			private final String NODE_LABEL = TwisterExtensionResources.getInstance().getString("node.label.CenterElement");

			/**
			 * @param config
			 */
			public CenterNode(final SpotConfig config) {
				super(config.getExtensionId() + ".center", config.getCenterElement());
				setNodeLabel(NODE_LABEL);
			}
		}
	}
}
