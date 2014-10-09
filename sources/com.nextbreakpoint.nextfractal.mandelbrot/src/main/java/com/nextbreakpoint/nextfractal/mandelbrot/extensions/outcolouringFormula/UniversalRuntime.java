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

import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class UniversalRuntime extends AbstractOutcolouringPaletteRuntime<UniversalConfig> {
	private ColorRendererRuntimeElement rendererElement;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringFormulaRuntime#isSolidGuessAllowed()
	 */
	@Override
	public boolean isSolidGuessAllowed() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringPaletteRuntime#prepareForRendering(int)
	 */
	@Override
	public void prepareForRendering(final RenderingFormulaExtensionRuntime<?> formulaRuntime, final int maxColors) {
		super.prepareForRendering(formulaRuntime, maxColors);
		if (rendererElement.getRendererRuntime() != null) {
			rendererElement.getRendererRuntime().prepareForRendering();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringPaletteRuntime#renderIndex(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	protected int renderIndex(final RenderedPoint cp) {
		if (rendererElement.getRendererRuntime() != null) {
			return (int) Math.rint(rendererElement.getRendererRuntime().renderColor(cp) * (colorTable.length - 1));
		}
		else {
			return 0;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean rendererChanged = (rendererElement != null) && rendererElement.isChanged();
		return super.isChanged() || rendererChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringPaletteRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		super.configReloaded();
		rendererElement = new ColorRendererRuntimeElement(getConfig().getColorRendererElement());
	}
}
