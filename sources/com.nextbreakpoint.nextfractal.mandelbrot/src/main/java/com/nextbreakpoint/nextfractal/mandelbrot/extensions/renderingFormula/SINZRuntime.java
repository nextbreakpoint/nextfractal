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
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class SINZRuntime extends AbstractRenderingFormulaRuntime<SINZConfig> {
	private static final int GUARD_VALUE = 1000;
	private double ta;
	private double tb;
	private double tc;
	private double td;
	private double te;
	private double tf;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime#getInitialPoint()
	 */
	@Override
	public Complex getInitialPoint() {
		return new Complex(Math.PI / 2.0, 0.0);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isHorizontalSymetryAllowed()
	 */
	@Override
	public boolean isHorizontalSymetryAllowed() {
		if (formulaRuntime != null) {
			return false;
		}
		if (orbitTrapRuntime != null) {
			return false;
		}
		return true;
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
		cp.zr = cp.xr;
		cp.zi = cp.xi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			ta = Math.cos(cp.zr);
			tb = Math.sin(cp.zr);
			tc = Math.exp(+cp.zi);
			td = Math.exp(-cp.zi);
			te = tb * 0.5d * (tc + td);
			tf = ta * 0.5d * (tc - td);
			cp.zr = (cp.wr * te) - (cp.wi * tf);
			cp.zi = (cp.wi * te) + (cp.wr * tf);
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
				if (Math.abs(cp.zi) > threshold) {
					cp.time = k;
					break;
				}
				if (((cp.zr * cp.zr) + (cp.zi * cp.zi)) < 0.0000001d) {
					cp.time = k;
					break;
				}
			}
			if (cp.zi > GUARD_VALUE) {
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
		double ta = 0.0;
		double tb = 0.0;
		double tc = 0.0;
		double td = 0.0;
		double te = 0.0;
		double tf = 0.0;
		cp.zr = cp.xr;
		cp.zi = cp.xi;
		cp.time = 0;
		for (int k = 1; k <= iterations; k++) {
			ta = Math.cos(cp.zr);
			tb = Math.sin(cp.zr);
			tc = Math.exp(+cp.zi);
			td = Math.exp(-cp.zi);
			te = tb * 0.5d * (tc + td);
			tf = ta * 0.5d * (tc - td);
			cp.zr = (cp.wr * te) - (cp.wi * tf);
			cp.zi = (cp.wi * te) + (cp.wr * tf);
			orbit.add(new Complex(cp.zr, cp.zi));
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				if (Math.abs(cp.zi) > threshold) {
					cp.time = k;
					break;
				}
				if (((cp.zr * cp.zr) + (cp.zi * cp.zi)) < 0.0000001d) {
					cp.time = k;
					break;
				}
			}
			if (cp.zi > GUARD_VALUE) {
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
