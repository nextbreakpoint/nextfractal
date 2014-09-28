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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class CircleTrapRuntime extends AbstractOrbitTrapRuntime<CircleTrapConfig> {
	private double r;
	private double t;
	private double m;
	private int c;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap.AbstractOrbitTrapRuntime#prepareForProcessing(com.nextbreakpoint.nextfractal.core.util.DoubleVector2D)
	 */
	@Override
	public void prepareForProcessing(final DoubleVector2D center) {
		super.prepareForProcessing(center);
		r = getConfig().getSize();
		t = getConfig().getThreshold();
		c = getConfig().getCriteria();
		if (t < 0) {
			t = 0;
		}
		if (r < t) {
			r = t;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime#processPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public boolean processPoint(final RenderedPoint cp) {
		cp.dr = cp.zr - center.getX();
		cp.di = cp.zi + center.getY();
		m = Math.sqrt(cp.dr * cp.dr + cp.di * cp.di);
		if (c == CriteriaElement.FIRST_OUT) {
			return (m < (r - t)) || (m > (r + t));
		}
		else {
			return (m > (r - t)) && (m < (r + t));
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
		GeneralPath path = new GeneralPath();
		Ellipse2D.Double ellipse1 = new Ellipse2D.Double((-getConfig().getSize() / 2d - getConfig().getThreshold()) * sx, (-getConfig().getSize() / 2d - getConfig().getThreshold()) * sy, (getConfig().getSize() + (2 * getConfig().getThreshold())) * sx, (getConfig().getSize() + (2 * getConfig().getThreshold())) * sy);
		Ellipse2D.Double ellipse2 = new Ellipse2D.Double((-getConfig().getSize() / 2d + getConfig().getThreshold()) * sx, (-getConfig().getSize() / 2d + getConfig().getThreshold()) * sy, (getConfig().getSize() - (2 * getConfig().getThreshold())) * sx, (getConfig().getSize() - (2 * getConfig().getThreshold())) * sy);
		path.append(ellipse1, false);
		path.append(ellipse2, false);
		return path;
	}
}
