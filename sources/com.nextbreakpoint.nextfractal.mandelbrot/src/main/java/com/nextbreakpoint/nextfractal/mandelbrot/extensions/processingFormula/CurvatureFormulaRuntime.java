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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.processingFormula;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class CurvatureFormulaRuntime extends ProcessingFormulaExtensionRuntime {
	private final Complex z0 = new Complex();
	private final Complex z1 = new Complex();
	private final Complex z2 = new Complex();
	private final Complex t1 = new Complex();
	private final Complex t2 = new Complex();
	private final Complex t3 = new Complex();
	private double sumMod;
	private double sumArg;
	private double avgMod;
	private double avgArg;
	private boolean flag;
	private int k;
	private double m;
	private double c;
	private double r;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime#prepareForProcessing()
	 */
	@Override
	public void prepareForProcessing() {
		k = 0;
		z0.r = 0;
		z0.i = 0;
		z1.r = 0;
		z1.i = 0;
		z2.r = 0;
		z2.i = 0;
		sumMod = 0;
		sumArg = 0;
		avgMod = 0;
		avgArg = 0;
		flag = false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime#processPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public void processPoint(final RenderedPoint cp) {
		if (!flag) {
			k += 1;
			z0.r = z1.r;
			z0.i = z1.i;
			z1.r = z2.r;
			z1.i = z2.i;
			z2.r = cp.zr;
			z2.i = cp.zi;
			if (k > 2) {
				Complex.sub(t1, z2, z1);
				Complex.sub(t2, z1, z0);
				Complex.div(t3, t1, t2);
				m = Complex.mod(t3);
				c = Math.abs(Complex.arg(t3));
				sumMod += m;
				sumArg += c;
				r = Complex.mod(t2);
				if (r < 0.000000001) {
					flag = true;
				}
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public void renderPoint(final RenderedPoint cp) {
		if (k > 2) {
			avgMod = sumMod / (k - 2);
			avgArg = sumArg / (k - 2);
		}
		cp.tr = avgMod * Math.cos(avgArg);
		cp.ti = avgMod * Math.sin(avgArg);
	}
}
