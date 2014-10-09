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
public class EXPZRuntime extends AbstractRenderingFormulaRuntime<EXPZConfig> {
	private static final int GUARD_VALUE = 1000;
	private double ta;
	private double tb;
	private double tc;

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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderPoint(final RenderedPoint cp) {
		if (formulaRuntime != null) {
			formulaRuntime.prepareForProcessing();
		}
		cp.zr = cp.xr;
		cp.zi = cp.xi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			tc = Math.exp(cp.zr);
			ta = Math.cos(cp.zi) * tc;
			tb = Math.sin(cp.zi) * tc;
			cp.zr = (cp.wr * ta) - (cp.wi * tb);
			cp.zi = (cp.wi * ta) + (cp.wr * tb);
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
				if (cp.zr > threshold) {
					cp.time = k;
					break;
				}
			}
			if (cp.zr > GUARD_VALUE) {
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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime#renderOrbit(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public List<Complex> renderOrbit(final RenderedPoint cp) {
		final ArrayList<Complex> orbit = new ArrayList<Complex>();
		double ta = 0.0;
		double tb = 0.0;
		double tc = 0.0;
		cp.zr = cp.xr;
		cp.zi = cp.xi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			tc = Math.exp(cp.zr);
			ta = Math.cos(cp.zi) * tc;
			tb = Math.sin(cp.zi) * tc;
			cp.zr = (cp.wr * ta) - (cp.wi * tb);
			cp.zi = (cp.wi * ta) + (cp.wr * tb);
			orbit.add(new Complex(cp.zr, cp.zi));
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (cp.zr > threshold) {
					cp.time = k;
					break;
				}
			}
			if (cp.zr > GUARD_VALUE) {
				cp.time = k;
				break;
			}
		}
		return orbit;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime#getNormalizedIterationCount(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public double getNormalizedIterationCount(final RenderedPoint cp) {
		return cp.time;// TODO not suported
	}
}
