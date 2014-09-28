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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRendererFormula;

import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public class SINRuntime extends PaletteRendererFormulaExtensionRuntime {
	private static final double CONSTANT_PI2 = 0.5d * Math.PI;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.extension.PaletteRendererFormulaExtensionRuntime#renderPalette(int)
	 */
	@Override
	public double[] renderPalette(final int size) {
		final double[] table = new double[size];
		final double delta = SINRuntime.CONSTANT_PI2 / size;
		double value = 0;
		for (int i = 0; i < size; i++) {
			table[i] = Math.sin(value);
			value += delta;
		}
		return table;
	}
}
