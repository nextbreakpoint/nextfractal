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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class ZNRuntime extends AbstractRenderingFormulaRuntime<ZNConfig> {
	private static final int GUARD_VALUE = 1000;
	private final Complex p2 = new Complex(0, 0);
	private final Complex p4 = new Complex(0, 0);
	private final Complex p8 = new Complex(0, 0);
	private final Complex p16 = new Complex(0, 0);
	private final Complex t = new Complex(0, 0);
	private final Complex z = new Complex(0, 0);
	private final Complex x = new Complex(0, 0);
	private final Complex w = new Complex(0, 0);
	private int exponent;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#prepareForRendering(com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime, com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime)
	 */
	@Override
	public void prepareForRendering(final ProcessingFormulaExtensionRuntime formulaRuntime, final OrbitTrapExtensionRuntime<?> orbitTrapRuntime) {
		super.prepareForRendering(formulaRuntime, orbitTrapRuntime);
		exponent = Math.abs(getExponent());
	}

	/**
	 * @return the exponent.
	 */
	public int getExponent() {
		return getConfig().getExponent();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isHorizontalSymetryAllowed()
	 */
	@Override
	public boolean isHorizontalSymetryAllowed() {
		return (exponent % 2 != 0);
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
			Complex.mul(p2, x, x);
			int e = exponent;
			z.r = 1;
			z.i = 0;
			if (e > 3) {
				Complex.mul(p4, p2, p2);
				if (e > 7) {
					Complex.mul(p8, p4, p4);
					if (e > 15) {
						Complex.mul(p16, p8, p8);
						final int d16 = e / 16;
						for (int i = 0; i < d16; i++) {
							Complex.mul(t, z, p16);
							z.r = t.r;
							z.i = t.i;
						}
						e = e % 16;
					}
					final int d8 = e / 8;
					for (int i = 0; i < d8; i++) {
						Complex.mul(t, z, p8);
						z.r = t.r;
						z.i = t.i;
					}
					e = e % 8;
				}
				final int d4 = e / 4;
				for (int i = 0; i < d4; i++) {
					Complex.mul(t, z, p4);
					z.r = t.r;
					z.i = t.i;
				}
				e = e % 4;
			}
			final int d2 = e / 2;
			for (int i = 0; i < d2; i++) {
				Complex.mul(t, z, p2);
				z.r = t.r;
				z.i = t.i;
			}
			e = e % 2;
			for (int i = 0; i < e; i++) {
				Complex.mul(t, z, x);
				z.r = t.r;
				z.i = t.i;
			}
			Complex.add(z, z, w);
			cp.zr = z.r;
			cp.zi = z.i;
			if (formulaRuntime != null) {
				formulaRuntime.processPoint(cp);
			}
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (Complex.mod(z) > threshold) {
					cp.time = k;
					break;
				}
			}
			x.r = cp.zr;
			x.i = cp.zi;
			if ((x.r > GUARD_VALUE) || (x.i > GUARD_VALUE)) {
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
		final Complex p2 = new Complex(0, 0);
		final Complex p4 = new Complex(0, 0);
		final Complex p8 = new Complex(0, 0);
		final Complex p16 = new Complex(0, 0);
		final Complex t = new Complex(0, 0);
		final Complex z = new Complex(0, 0);
		final Complex x = new Complex(cp.xr, cp.xi);
		final Complex w = new Complex(cp.wr, cp.wi);
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			Complex.mul(p2, x, x);
			int e = exponent;
			z.r = 1;
			z.i = 0;
			if (e > 3) {
				Complex.mul(p4, p2, p2);
				if (e > 7) {
					Complex.mul(p8, p4, p4);
					if (e > 15) {
						Complex.mul(p16, p8, p8);
						final int d16 = e / 16;
						for (int i = 0; i < d16; i++) {
							Complex.mul(t, z, p16);
							z.r = t.r;
							z.i = t.i;
						}
						e = e % 16;
					}
					final int d8 = e / 8;
					for (int i = 0; i < d8; i++) {
						Complex.mul(t, z, p8);
						z.r = t.r;
						z.i = t.i;
					}
					e = e % 8;
				}
				final int d4 = e / 4;
				for (int i = 0; i < d4; i++) {
					Complex.mul(t, z, p4);
					z.r = t.r;
					z.i = t.i;
				}
				e = e % 4;
			}
			final int d2 = e / 2;
			for (int i = 0; i < d2; i++) {
				Complex.mul(t, z, p2);
				z.r = t.r;
				z.i = t.i;
			}
			e = e % 2;
			for (int i = 0; i < e; i++) {
				Complex.mul(t, z, x);
				z.r = t.r;
				z.i = t.i;
			}
			Complex.add(z, z, w);
			cp.zr = z.r;
			cp.zi = z.i;
			orbit.add(new Complex(z.r, z.i));
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (Complex.mod(z) > threshold) {
					cp.time = k;
					break;
				}
			}
			x.r = z.r;
			x.i = z.i;
			if ((x.r > GUARD_VALUE) || (x.i > GUARD_VALUE)) {
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
		final double modulus = Math.sqrt((cp.zr * cp.zr) + (cp.zi * cp.zi));
		// return cp.time + 1d - Math.log(Math.log(modulus)) / Math.log(exponent);
		return cp.time + (Math.log(Math.log(threshold)) - Math.log(Math.log(modulus))) / Math.log(exponent);
	}
}
