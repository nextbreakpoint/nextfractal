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

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class BinaryAndPotentialRuntime extends AbstractOutcolouringPaletteRuntime<BinaryAndPotentialConfig> {
	private static final double CONSTANT_2PI = 2d * Math.PI;
	private int offset;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringPaletteRuntime#prepareForRendering(int)
	 */
	@Override
	public void prepareForRendering(final RenderingFormulaExtensionRuntime<?> formulaRuntime, final int iterations) {
		super.prepareForRendering(formulaRuntime, iterations);
		final BinaryAndPotentialConfig config = getConfig();
		offset = (int) Math.rint((config.getOffset().intValue() * colorTable.length) / 100);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime#renderIndex(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	protected int renderIndex(final RenderedPoint cp) {
		final double phase = Math.atan2(cp.zi, cp.zr) / BinaryAndPotentialRuntime.CONSTANT_2PI;
		if (phase > 0) {
			return ((cp.time - 1) + offset) % colorTable.length;
		}
		else {
			return (cp.time - 1) % colorTable.length;
		}
	}
}
