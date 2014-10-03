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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap;

import java.awt.Shape;
import java.awt.geom.Line2D;

import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class LineTrapRuntime extends AbstractOrbitTrapRuntime<LineTrapConfig> {
	private double l;
	private double a;
	private double t;
	private int c;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap.AbstractOrbitTrapRuntime#prepareForProcessing(com.nextbreakpoint.nextfractal.core.util.DoubleVector2D)
	 */
	@Override
	public void prepareForProcessing(final DoubleVector2D center) {
		super.prepareForProcessing(center);
		l = getConfig().getLength() / 2d;
		a = (getConfig().getRotation() * Math.PI) / 180d;
		t = getConfig().getThreshold();
		c = getConfig().getCriteria();
		if (l < 0) {
			l = 0;
		}
		if (t < 0) {
			t = 0;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime#processPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public boolean processPoint(final RenderedPoint cp) {
		cp.dr = cp.zr - center.getX();
		cp.di = cp.zi + center.getY();
		double tr = cp.dr * Math.cos(a) - cp.di * Math.sin(a);
		double ti = cp.di * Math.cos(a) + cp.dr * Math.sin(a);
		if (c == CriteriaElement.FIRST_OUT) {
			return (Math.abs(ti) > t) || (Math.abs(tr) > (l + t)) || ((Math.abs(tr) > l) && ((Math.sqrt(Math.pow(Math.abs(tr) - l, 2d) + ti * ti)) > t));
		}
		else {
			return ((Math.abs(ti) < t) && (Math.abs(tr) < l)) || ((Math.sqrt(Math.pow(Math.abs(tr) - l, 2d) + ti * ti)) < t);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public void renderPoint(final RenderedPoint cp) {
		cp.tr = cp.dr;
		cp.ti = cp.di;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime#renderOrbitTrap(double, double)
	 */
	@Override
	public Shape renderOrbitTrap(final double sx, final double sy, final double theta) {
		double ca = Math.cos(a + theta);
		double sa = Math.sin(a + theta);
		double tr1 = (+l * ca) * sx;
		double ti1 = (-l * sa) * sy;
		double tr2 = (-l * ca) * sx;
		double ti2 = (+l * sa) * sy;
		Line2D.Double line = new Line2D.Double(tr1, ti1, tr2, ti2);
		return line;
	}
}
