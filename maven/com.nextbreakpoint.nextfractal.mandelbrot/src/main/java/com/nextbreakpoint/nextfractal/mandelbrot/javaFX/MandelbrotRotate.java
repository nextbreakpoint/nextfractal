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

public class MandelbrotRotate implements MandelbrotTool {
	private MandelbrotToolContext context;
	private volatile boolean pressed;
	private volatile boolean changed;
	private volatile boolean active;
	private double x0;
	private double y0;
	private double x1;
	private double y1;
	private double a0;
	private double a1;
	private double r0;
	private double i0;

	public MandelbrotRotate(MandelbrotToolContext context) {
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
		if (active) {
			changed = true;
		}
	}

	@Override
	public void released(MouseEvent e) {
		x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
		y1 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		pressed = false;
		active = !active;
	}

	@Override
	public void pressed(MouseEvent e) {
		if (active) {
			x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
			y1 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
			MandelbrotView oldView = context.getMandelbrotSession().getViewAsCopy();
			double[] t = oldView.getTraslation();
			double[] r = oldView.getRotation();
//			double a = -r[2] * Math.PI / 180;
			a0 = r[2] * Math.PI / 180;
			a1 = a0 - Math.atan2(y1 - y0, x1 - x0);
			r0 = t[0];//-(Math.cos(a) * t[0] + Math.sin(a) * t[1]);
			i0 = t[1];//-(Math.cos(a) * t[1] - Math.sin(a) * t[0]);
		} else {
			x1 = x0 = (e.getX() - context.getWidth() / 2) / context.getWidth();
			y1 = y0 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		}
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
			double a = a1 + Math.atan2(y1 - y0, x1 - x0);
			Number size = context.getInitialSize();
			Number center = context.getInitialCenter();
			double px = center.r() - x0 * z * size.r();
			double py = center.i() - y0 * z * size.r();
			System.out.println("1) " + px + "," + py);
			double tpx = (Math.cos(a) * px + Math.sin(a) * py);
			double tpy = (Math.cos(a) * py - Math.sin(a) * px);
			System.out.println("2) " + tpx + "," + tpy);
			x = r0 + tpx - px;
			y = i0 + tpy - py;
			a = a * 180 / Math.PI;
			System.out.println("3) " + a);
			MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, a, r[3] }, s, p, j);
			context.getMandelbrotSession().setView(view, pressed);
			changed = false;
		}
	}
}
