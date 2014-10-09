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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula;

import com.nextbreakpoint.nextfractal.core.common.ComplexElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ThresholdElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.MandelbrotExtensionResources;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractRenderingFormulaConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((RenderingFormulaExtensionConfig) config);
	}

	/**
	 * @param parentNode
	 */
	public void createNodes(final Node parentNode, final RenderingFormulaExtensionConfig config) {
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<RenderingFormulaExtensionConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final RenderingFormulaExtensionConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeBuilder#createNodes(Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new IterationsNode(getConfig()));
			parentNode.appendChildNode(new ThresholdNode(getConfig()));
			parentNode.appendChildNode(new CenterNode(getConfig()));
			parentNode.appendChildNode(new ScaleNode(getConfig()));
			AbstractRenderingFormulaConfigNodeBuilderRuntime.this.createNodes(parentNode, getConfig());
		}

		protected class IterationsNode extends IterationsElementNode {
			/**
			 * @param config
			 */
			public IterationsNode(final RenderingFormulaExtensionConfig config) {
				super(config.getExtensionId() + ".iterations", config.getIterationsElement());
			}
		}

		private class ThresholdNode extends ThresholdElementNode {
			/**
			 * @param config
			 */
			public ThresholdNode(final RenderingFormulaExtensionConfig config) {
				super(config.getExtensionId() + ".threshold", config.getThresholdElement());
			}
		}

		private class CenterNode extends ComplexElementNode {
			/**
			 * @param config
			 */
			public CenterNode(final RenderingFormulaExtensionConfig config) {
				super(config.getExtensionId() + ".center", config.getCenterElement());
				setNodeLabel(MandelbrotExtensionResources.getInstance().getString("node.label.CenterElement"));
			}
		}

		private class ScaleNode extends ComplexElementNode {
			/**
			 * @param config
			 */
			public ScaleNode(final RenderingFormulaExtensionConfig config) {
				super(config.getExtensionId() + ".scale", config.getScaleElement());
				setNodeLabel(MandelbrotExtensionResources.getInstance().getString("node.label.ScaleElement"));
			}
		}
	}
}
