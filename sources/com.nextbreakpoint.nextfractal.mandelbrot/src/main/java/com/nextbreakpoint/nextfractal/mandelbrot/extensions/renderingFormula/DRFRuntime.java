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
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.processingFormula.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class DRFRuntime extends AbstractRenderingFormulaRuntime<DRFConfig> {
	private static final int GUARD_VALUE = 1000;
	private double ta;
	private double tb;
	private double zr;
	private double zi;
	private double d0;
	private double d1;
	private double l0;
	private double l1;
	private double exponent;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#prepareForRendering(com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.processingFormula.ProcessingFormulaExtensionRuntime, com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime)
	 */
	@Override
	public void prepareForRendering(final ProcessingFormulaExtensionRuntime formulaRuntime, final OrbitTrapExtensionRuntime<?> orbitTrapRuntime) {
		super.prepareForRendering(formulaRuntime, orbitTrapRuntime);
		exponent = getExponent();
	}

	/**
	 * @return the exponent.
	 */
	public double getExponent() {
		return getConfig().getExponent();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isHorizontalSymetryAllowed()
	 */
	@Override
	public boolean isHorizontalSymetryAllowed() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaRuntime#isVerticalSymetryAllowed()
	 */
	@Override
	public boolean isVerticalSymetryAllowed() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionRuntime#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public int renderPoint(final RenderedPoint cp) {
		if (formulaRuntime != null) {
			formulaRuntime.prepareForProcessing();
		}
		cp.time = 0;
		zr = cp.xr;
		zi = cp.xi;
		ta = Math.pow(Math.sqrt((zr * zr) + (zi * zi)), exponent);
		tb = Math.atan2(zi, zr) * exponent;
		zr = ta * Math.cos(tb) + cp.wr;
		zi = ta * Math.sin(tb) + cp.wi;
		cp.zr = zr - cp.xr;
		cp.zi = zi - cp.xi;
		l1 = 0;
		l0 = 0;
		d0 = Math.sqrt((cp.zr * cp.zr) + (cp.zi * cp.zi));
		for (int k = 2; k <= iterations; k++) {
			ta = Math.pow(Math.sqrt((zr * zr) + (zi * zi)), exponent);
			tb = Math.atan2(zi, zr) * exponent;
			zr = ta * Math.cos(tb) + cp.wr;
			zi = ta * Math.sin(tb) + cp.wi;
			cp.zr = zr - cp.xr;
			cp.zi = zi - cp.xi;
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
				d1 = Math.sqrt((cp.zr * cp.zr) + (cp.zi * cp.zi));
				l1 = d1 / d0;
				if (Math.abs(l1 - l0) < threshold) {
					cp.time = k;
					break;
				}
				if (Math.abs(l1 - l0) > 1000) {
					cp.time = k;
					break;
				}
				if (d1 < 0.000000001) {
					cp.time = k;
					break;
				}
				d0 = d1;
				l0 = l1;
			}
			if ((cp.zr > GUARD_VALUE) || (cp.zi > GUARD_VALUE)) {
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
		double ta;
		double tb;
		double zr;
		double zi;
		double d0;
		double d1;
		double l0;
		double l1;
		cp.time = 0;
		zr = cp.xr;
		zi = cp.xi;
		ta = Math.pow(Math.sqrt((zr * zr) + (zi * zi)), exponent);
		tb = Math.atan2(zi, zr) * exponent;
		zr = ta * Math.cos(tb) + cp.wr;
		zi = ta * Math.sin(tb) + cp.wi;
		cp.zr = zr - cp.xr;
		cp.zi = zi - cp.xi;
		l1 = 0;
		l0 = 0;
		orbit.add(new Complex(cp.zr, cp.zi));
		d0 = Math.sqrt((cp.zr * cp.zr) + (cp.zi * cp.zi));
		for (int k = 2; k <= iterations; k++) {
			ta = Math.pow(Math.sqrt((zr * zr) + (zi * zi)), exponent);
			tb = Math.atan2(zi, zr) * exponent;
			zr = ta * Math.cos(tb) + cp.wr;
			zi = ta * Math.sin(tb) + cp.wi;
			cp.zr = zr - cp.xr;
			cp.zi = zi - cp.xi;
			orbit.add(new Complex(cp.zr, cp.zi));
			if (orbitTrapRuntime != null) {
				if (orbitTrapRuntime.processPoint(cp)) {
					cp.time = k;
					break;
				}
			}
			else {
				d1 = Math.sqrt((cp.zr * cp.zr) + (cp.zi * cp.zi));
				l1 = d1 / d0;
				if (Math.abs(l1 - l0) < threshold) {
					cp.time = k;
					break;
				}
				if (Math.abs(l1 - l0) > 1000) {
					cp.time = k;
					break;
				}
				if (d1 < 0.000000001) {
					cp.time = k;
					break;
				}
				d0 = d1;
				l0 = l1;
			}
			if ((cp.zr > GUARD_VALUE) || (cp.zi > GUARD_VALUE)) {
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
		return cp.time;
	}
}
