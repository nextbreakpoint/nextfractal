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
package com.nextbreakpoint.nextfractal.mandelbrot.util;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class DefaultRenderedPaletteParam extends RenderedPaletteParam {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_PALETTE_RENDERER_FORMULA_EXTENSION_ID = "twister.mandelbrot.palette.renderer.formula.lin";

	/**
	 * @param size
	 */
	public DefaultRenderedPaletteParam(final double size) {
		super(DefaultRenderedPaletteParam.buildFormulas(), new int[] { 0xFF000000, 0xFFFFFFFF }, size);
	}

	/**
	 * @param colors
	 * @param size
	 */
	public DefaultRenderedPaletteParam(final int[] colors, final double size) {
		super(DefaultRenderedPaletteParam.buildFormulas(), colors, size);
	}

	/**
	 * @return
	 * @throws Error
	 */
	private static ExtensionReference[] buildFormulas() throws Error {
		final ExtensionReference[] formulas = new ExtensionReference[4];
		try {
			final Extension<PaletteRendererFormulaExtensionRuntime> extension = MandelbrotRegistry.getInstance().getPaletteRendererFormulaExtension(DefaultRenderedPaletteParam.DEFAULT_PALETTE_RENDERER_FORMULA_EXTENSION_ID);
			formulas[0] = extension.getExtensionReference();
			formulas[1] = extension.getExtensionReference();
			formulas[2] = extension.getExtensionReference();
			formulas[3] = extension.getExtensionReference();
		}
		catch (final Exception e) {
			throw new Error(e);
		}
		return formulas;
	}
}
