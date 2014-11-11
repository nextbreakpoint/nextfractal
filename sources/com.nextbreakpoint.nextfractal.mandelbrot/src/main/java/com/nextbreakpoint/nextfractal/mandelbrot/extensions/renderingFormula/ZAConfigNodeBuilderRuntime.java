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

import com.nextbreakpoint.nextfractal.core.common.DoubleElementNode;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.MandelbrotExtensionResources;

/**
 * @author Andrea Medeghini
 */
public class ZAConfigNodeBuilderRuntime extends AbstractRenderingFormulaConfigNodeBuilderRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaConfigNodeBuilderRuntime#createNodes(com.nextbreakpoint.nextfractal.core.tree.NodeObject, com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig)
	 */
	@Override
	public void createNodes(final NodeObject parentNode, final RenderingFormulaExtensionConfig config) {
		parentNode.appendChildNode(new ExponentNode((ZAConfig) config));
	}

	private class ExponentNode extends DoubleElementNode {
		/**
		 * @param config
		 */
		public ExponentNode(final ZAConfig config) {
			super(config.getExtensionId() + ".exponent", config.getExponentElement());
			setNodeLabel(MandelbrotExtensionResources.getInstance().getString("node.label.ExponentElement"));
		}
	}
}
