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
public class BinaryRuntime extends AbstractOutcolouringFormulaRuntime<BinaryConfig> {
	private final int[] colorTable = new int[2];

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaRuntime#prepareForRendering(int)
	 */
	@Override
	public void prepareForRendering(final RenderingFormulaExtensionRuntime<?> formulaRuntime, final int maxColors) {
		final BinaryConfig config = getConfig();
		colorTable[0] = config.getColors()[0].getARGB();
		colorTable[1] = config.getColors()[1].getARGB();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime#renderColor(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderColor(final RenderedPoint cp) {
		return colorTable[renderIndex(cp)];
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionRuntime#renderColor(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint, int)
	 */
	@Override
	public int renderColor(final RenderedPoint cp, final int shift) {
		return this.renderColor(cp);
	}

	/**
	 * @param cp
	 * @return the index.
	 */
	protected int renderIndex(final RenderedPoint cp) {
		final double phase = Math.atan2(cp.zi, cp.zr);
		return (phase > 0) ? 0 : 1;
	}
}
