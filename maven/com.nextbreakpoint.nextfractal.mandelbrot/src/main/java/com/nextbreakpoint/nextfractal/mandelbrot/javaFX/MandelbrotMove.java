/*
 * NextFractal 1.1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.scene.input.MouseEvent;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class MandelbrotMove implements MandelbrotTool {
	private MandelbrotToolContext context;
	private volatile boolean pressed;
	private volatile boolean changed;
	private double x0;
	private double y0;
	private double x1;
	private double y1;

	public MandelbrotMove(MandelbrotToolContext context) {
		this.context = context;
	}
	
	@Override
	public void clicked(MouseEvent e) {
	}

	@Override
	public void moved(MouseEvent e) {
	}

	@Override
	public void dragged(MouseEvent e) {
		x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
		y1 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		changed = true;
	}

	@Override
	public void released(MouseEvent e) {
		x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
		y1 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		pressed = false;
		changed = true;
	}

	@Override
	public void pressed(MouseEvent e) {
		x1 = x0 = (e.getX() - context.getWidth() / 2) / context.getWidth();
		y1 = y0 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		pressed = true;
	}

	@Override
	public void update(long time) {
		if (changed) {
			MandelbrotView oldView = context.getMandelbrotSession().getViewAsCopy();
			double[] t = oldView.getTraslation();
			double[] r = oldView.getRotation();
			double[] s = oldView.getScale();
			double[] p = oldView.getPoint();
			boolean j = oldView.isJulia();
			double x = t[0];
			double y = t[1];
			double z = t[2];
			double a = r[2] * Math.PI / 180;
			Number size = context.getInitialSize();
			x -= z * size.r() * (Math.cos(a) * (x1 - x0) + Math.sin(a) * (y1 - y0));
			y -= z * size.i() * (Math.cos(a) * (y1 - y0) - Math.sin(a) * (x1 - x0));
			x0 = x1;
			y0 = y1;
			MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s, p, j);
			context.getMandelbrotSession().setView(view, pressed);
			changed = false;
		}
	}
}
