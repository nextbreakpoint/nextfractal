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

import com.nextbreakpoint.nextfractal.core.util.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.PaletteRendererRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractOutcolouringPaletteRuntime<T extends AbstractOutcolouringPaletteConfig> extends AbstractOutcolouringFormulaRuntime<T> {
	protected RenderingFormulaExtensionRuntime<?> formulaRuntime;
	private PaletteRendererRuntimeElement rendererElement;
	protected int[] colorTable;
	protected Palette palette;
	protected int maxColors;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime#prepareForRendering(com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime, int)
	 */
	@Override
	public void prepareForRendering(final RenderingFormulaExtensionRuntime<?> formulaRuntime, final int maxColors) {
		this.formulaRuntime = formulaRuntime;
		if (rendererElement.getRendererRuntime() != null) {
			rendererElement.getRendererRuntime().prepareForRendering();
			final Palette newPalette = rendererElement.getRendererRuntime().renderPalette();
			if ((this.maxColors != maxColors) || (colorTable == null) || (palette != newPalette)) {
				this.colorTable = newPalette.renderTable(maxColors);
			}
			this.maxColors = maxColors;
			this.palette = newPalette;
		}
		else {
			this.maxColors = 0;
			this.palette = null;
			this.colorTable = new int[] { 0xFF000000 };
		}
	}

	/**
	 * @param cp
	 * @return the color.
	 */
	@Override
	public int renderColor(final RenderedPoint cp) {
		if (this.colorTable.length == 0) {
			return 0;
		}
		int index = this.renderIndex(cp);
		if (index < 0) {
			index = 0;
		}
		return this.colorTable[index % this.colorTable.length];
	}

	/**
	 * @param cp
	 * @param shift
	 * @return the color.
	 */
	@Override
	public int renderColor(final RenderedPoint cp, final int shift) {
		if (shift != 0) {
			if (this.colorTable.length == 0) {
				return 0;
			}
			int index = this.renderIndex(cp) + shift;
			if (index < 0) {
				index = 0;
			}
			return this.colorTable[index % this.colorTable.length];
		}
		else {
			return this.renderColor(cp);
		}
	}

	/**
	 * @param cp
	 * @return the index.
	 */
	protected abstract int renderIndex(RenderedPoint cp);

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#isChanged()
	 */
	@Override
	public boolean isChanged() {
		final boolean rendererChanged = (rendererElement != null) && rendererElement.isChanged();
		return super.isChanged() || rendererChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		super.configReloaded();
		rendererElement = new PaletteRendererRuntimeElement(getConfig().getPaletteRendererElement());
	}
}
