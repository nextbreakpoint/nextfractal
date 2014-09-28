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
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public class RectangleTrapRuntime extends AbstractOrbitTrapRuntime<RectangleTrapConfig> {
	private double ti;
	private double tr;
	private double w;
	private double h;
	private double a;
	private int c;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap.AbstractOrbitTrapRuntime#prepareForProcessing(com.nextbreakpoint.nextfractal.core.util.DoubleVector2D)
	 */
	@Override
	public void prepareForProcessing(final DoubleVector2D center) {
		super.prepareForProcessing(center);
		w = getConfig().getWidth() / 2d;
		h = getConfig().getHeight() / 2d;
		a = (getConfig().getRotation() * Math.PI) / 180d;
		c = getConfig().getCriteria();
		if (w < 0) {
			w = 0;
		}
		if (h < 0) {
			h = 0;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime#processPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint)
	 */
	@Override
	public boolean processPoint(final RenderedPoint cp) {
		cp.dr = cp.zr - center.getX();
		cp.di = cp.zi + center.getY();
		tr = cp.dr * Math.cos(a) - cp.di * Math.sin(a);
		ti = cp.di * Math.cos(a) + cp.dr * Math.sin(a);
		if (c == CriteriaElement.FIRST_OUT) {
			return (Math.abs(tr) > w) || (Math.abs(ti) > h);
		}
		else {
			return (Math.abs(tr) < w) && (Math.abs(ti) < h);
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
		double ca = Math.cos(a + theta);
		double sa = Math.sin(a + theta);
		double tr1 = (-w * ca - h * sa) * sx;
		double ti1 = (-h * ca + w * sa) * sy;
		double tr2 = (+w * ca - h * sa) * sx;
		double ti2 = (-h * ca - w * sa) * sy;
		double tr3 = (+w * ca + h * sa) * sx;
		double ti3 = (+h * ca - w * sa) * sy;
		double tr4 = (-w * ca + h * sa) * sx;
		double ti4 = (+h * ca + w * sa) * sy;
		Line2D.Double line1 = new Line2D.Double(tr1, ti1, tr2, ti2);
		Line2D.Double line2 = new Line2D.Double(tr2, ti2, tr3, ti3);
		Line2D.Double line3 = new Line2D.Double(tr3, ti3, tr4, ti4);
		Line2D.Double line4 = new Line2D.Double(tr4, ti4, tr1, ti1);
		path.append(line1, false);
		path.append(line2, false);
		path.append(line3, false);
		path.append(line4, false);
		return path;
	}
}
