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

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class NEWTON3Runtime extends AbstractRenderingFormulaRuntime<NEWTON3Config> {
	private static final int GUARD_VALUE = 1000000;
	private final Complex ta = new Complex(0, 0);
	private final Complex tb = new Complex(0, 0);
	private final Complex tc = new Complex(0, 0);
	private final Complex td = new Complex(0, 0);
	private final Complex x = new Complex(0, 0);
	private final Complex w = new Complex(0, 0);

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isMandelbrotModeAllowed()
	 */
	@Override
	public boolean isMandelbrotModeAllowed() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isVerticalSymetryAllowed()
	 */
	@Override
	public boolean isVerticalSymetryAllowed() {
		if (formulaRuntime != null) {
			return false;
		}
		if (orbitTrapRuntime != null) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderPoint(final RenderedPoint cp) {
		if (formulaRuntime != null) {
			formulaRuntime.prepareForProcessing();
		}
		x.r = cp.xr;
		x.i = cp.xi;
		w.r = cp.wr;
		w.i = cp.wi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			Complex.mul(ta, x, x);
			Complex.mul(tb, ta, x);
			Complex.sub(tc, tb, w);
			cp.zr = x.r;
			cp.zi = x.i;
			if (formulaRuntime != null) {
				formulaRuntime.processPoint(cp);
			}
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.zr -= w.r;
					cp.zi -= w.i;
					cp.time = k;
					break;
				}
			}
			else {
				if (Complex.mod(tc) < 0.05) {
					cp.zr -= w.r;
					cp.zi -= w.i;
					cp.time = k;
					break;
				}
			}
			Complex.mul(tc, tb, 2.0);
			Complex.add(tc, tc, w);
			Complex.mul(td, ta, 3.0);
			if (Complex.mod(td) < 0.0000000000000000000001) {
				td.r = 0.0000000000000000000001;
				td.i = 0.0;
			}
			Complex.div(ta, tc, td);
			x.r = ta.r;
			x.i = ta.i;
			if ((x.r > GUARD_VALUE) || (x.i > GUARD_VALUE)) {
				cp.zr -= w.r;
				cp.zi -= w.i;
				cp.time = k;
				break;
			}
		}
		if (formulaRuntime != null) {
			formulaRuntime.renderPoint(cp);
		}
		else {
			if (orbitTrapRuntime != null) {
				orbitTrapRuntime.renderPoint(cp);
			}
		}
		return cp.time;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#renderOrbit(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public List<Complex> renderOrbit(final RenderedPoint cp) {
		final ArrayList<Complex> orbit = new ArrayList<Complex>();
		final Complex ta = new Complex(0, 0);
		final Complex tb = new Complex(0, 0);
		final Complex tc = new Complex(0, 0);
		final Complex td = new Complex(0, 0);
		final Complex x = new Complex(cp.xr, cp.xi);
		final Complex w = new Complex(cp.wr, cp.wi);
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			Complex.mul(ta, x, x);
			Complex.mul(tb, ta, x);
			Complex.sub(tc, tb, w);
			cp.zr = x.r;
			cp.zi = x.i;
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.zr -= w.r;
					cp.zi -= w.i;
					orbit.add(new Complex(cp.zr, cp.zi));
					cp.time = k;
					break;
				}
			}
			else {
				if (Complex.mod(tc) < 0.05) {
					cp.zr -= w.r;
					cp.zi -= w.i;
					orbit.add(new Complex(cp.zr, cp.zi));
					cp.time = k;
					break;
				}
			}
			orbit.add(new Complex(cp.zr, cp.zi));
			Complex.mul(tc, tb, 2.0);
			Complex.add(tc, tc, w);
			Complex.mul(td, ta, 3.0);
			if (Complex.mod(td) < 0.0000000000000000000001) {
				td.r = 0.0000000000000000000001;
				td.i = 0.0;
			}
			Complex.div(ta, tc, td);
			x.r = ta.r;
			x.i = ta.i;
			if ((x.r > GUARD_VALUE) || (x.i > GUARD_VALUE)) {
				cp.zr -= w.r;
				cp.zi -= w.i;
				cp.time = k;
				break;
			}
		}
		return orbit;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#getNormalizedIterationCount(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public double getNormalizedIterationCount(final RenderedPoint cp) {
		return cp.time;// TODO not suported
	}
}
