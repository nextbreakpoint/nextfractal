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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.processed;

import com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringPaletteRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class PhaseAndPotentialRuntime extends AbstractOutcolouringPaletteRuntime<PhaseAndPotentialConfig> {
	private static final double CONSTANT_2PI = 2d * Math.PI;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringPaletteRuntime#renderIndex(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	protected int renderIndex(final RenderedPoint cp) {
		final double phase = Math.atan2(cp.ti, cp.tr) / PhaseAndPotentialRuntime.CONSTANT_2PI;
		if (phase > 0) {
			return (int) Math.rint((0.5d + 0.5d * phase) * (cp.time - 1));
		}
		else {
			return (int) Math.rint((0.5d + 0.5d * (1d + phase)) * (cp.time - 1));
		}
	}
}
