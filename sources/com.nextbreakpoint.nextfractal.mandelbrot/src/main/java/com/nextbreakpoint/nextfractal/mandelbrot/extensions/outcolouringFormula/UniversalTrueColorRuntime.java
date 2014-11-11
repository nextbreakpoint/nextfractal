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
public class UniversalTrueColorRuntime extends AbstractOutcolouringFormulaRuntime<UniversalTrueColorConfig> {
	private final ColorRendererRuntimeElement[] rendererElements = new ColorRendererRuntimeElement[4];

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaRuntime#isSolidGuessAllowed()
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
		if (rendererElements[0].getRendererRuntime() != null) {
			rendererElements[0].getRendererRuntime().prepareForRendering();
		}
		if (rendererElements[1].getRendererRuntime() != null) {
			rendererElements[1].getRendererRuntime().prepareForRendering();
		}
		if (rendererElements[2].getRendererRuntime() != null) {
			rendererElements[2].getRendererRuntime().prepareForRendering();
		}
		if (rendererElements[3].getRendererRuntime() != null) {
			rendererElements[3].getRendererRuntime().prepareForRendering();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime#renderColor(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderColor(final RenderedPoint cp) {
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		if (rendererElements[0].getRendererRuntime() != null) {
			r = (int) Math.rint(rendererElements[0].getRendererRuntime().renderColor(cp) * 255d);
		}
		if (rendererElements[1].getRendererRuntime() != null) {
			g = (int) Math.rint(rendererElements[1].getRendererRuntime().renderColor(cp) * 255d);
		}
		if (rendererElements[2].getRendererRuntime() != null) {
			b = (int) Math.rint(rendererElements[2].getRendererRuntime().renderColor(cp) * 255d);
		}
		if (rendererElements[3].getRendererRuntime() != null) {
			a = (int) Math.rint(rendererElements[3].getRendererRuntime().renderColor(cp) * 255d);
		}
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionRuntime#renderColor(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderColor(final RenderedPoint cp, final int shift) {
		return this.renderColor(cp);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean isChanged = super.isChanged();
		if (rendererElements[0] != null) {
			isChanged |= rendererElements[0].isChanged();
		}
		if (rendererElements[1] != null) {
			isChanged |= rendererElements[1].isChanged();
		}
		if (rendererElements[2] != null) {
			isChanged |= rendererElements[2].isChanged();
		}
		if (rendererElements[3] != null) {
			isChanged |= rendererElements[3].isChanged();
		}
		return isChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		super.configReloaded();
		rendererElements[0] = new ColorRendererRuntimeElement(getConfig().getColorRendererElement(0));
		rendererElements[1] = new ColorRendererRuntimeElement(getConfig().getColorRendererElement(1));
		rendererElements[2] = new ColorRendererRuntimeElement(getConfig().getColorRendererElement(2));
		rendererElements[3] = new ColorRendererRuntimeElement(getConfig().getColorRendererElement(3));
	}
}
