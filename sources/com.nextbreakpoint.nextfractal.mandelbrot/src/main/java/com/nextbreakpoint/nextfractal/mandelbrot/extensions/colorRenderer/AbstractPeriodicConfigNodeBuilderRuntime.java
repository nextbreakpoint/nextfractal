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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.colorRenderer;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementNode;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.ColorRendererFormulaConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.MandelbrotExtensionResources;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementNode;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractPeriodicConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((AbstractPeriodicConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<AbstractPeriodicConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final AbstractPeriodicConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ColorRendererFormulaConfigElementNode(getConfig().getColorRendererFormulaElement()));
			parentNode.appendChildNode(new AmplitudeNode(getConfig()));
			parentNode.appendChildNode(new FrequencyNode(getConfig()));
			parentNode.appendChildNode(new ScaleNode(getConfig()));
			parentNode.appendChildNode(new TimeEnableNode(getConfig()));
			parentNode.appendChildNode(new AbsoluteEnableNode(getConfig()));
		}
	}

	protected static class AmplitudeNode extends PercentageElementNode {
		private static final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.periodic.AmplitudeElement");

		/**
		 * @param config
		 */
		public AmplitudeNode(final AbstractPeriodicConfig config) {
			super(config.getExtensionId() + ".amplitude", config.getAmplitudeElement());
			setNodeLabel(AmplitudeNode.NODE_LABEL);
		}
	}

	protected static class FrequencyNode extends DoubleElementNode {
		private static final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.periodic.FrequencyElement");

		/**
		 * @param config
		 */
		public FrequencyNode(final AbstractPeriodicConfig config) {
			super(config.getExtensionId() + ".frequency", config.getFrequencyElement());
			setNodeLabel(FrequencyNode.NODE_LABEL);
		}
	}

	protected static class ScaleNode extends DoubleElementNode {
		private static final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.periodic.ScaleElement");

		/**
		 * @param config
		 */
		public ScaleNode(final AbstractPeriodicConfig config) {
			super(config.getExtensionId() + ".scale", config.getScaleElement());
			setNodeLabel(ScaleNode.NODE_LABEL);
		}
	}

	protected static class AbsoluteEnableNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.periodic.AbsoluteEnableElement");

		/**
		 * @param config
		 */
		public AbsoluteEnableNode(final AbstractPeriodicConfig config) {
			super(config.getExtensionId() + ".absoluteEnable", config.getAbsoluteEnabledElement());
			setNodeLabel(AbsoluteEnableNode.NODE_LABEL);
		}
	}

	protected static class TimeEnableNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotExtensionResources.getInstance().getString("node.label.periodic.TimeEnableElement");

		/**
		 * @param config
		 */
		public TimeEnableNode(final AbstractPeriodicConfig config) {
			super(config.getExtensionId() + ".timeEnable", config.getTimeEnabledElement());
			setNodeLabel(TimeEnableNode.NODE_LABEL);
		}
	}
}
