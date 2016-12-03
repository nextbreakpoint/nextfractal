/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotMetadata;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import javafx.scene.input.MouseEvent;

public class ToolZoom implements Tool {
	private ToolContext context;
	private volatile boolean pressed;
	private volatile boolean changed;
	private volatile boolean redraw;
	private boolean primary;
	private boolean zoomin;
	private double x1;
	private double y1;

	public ToolZoom(ToolContext context, boolean zoomin) {
		this.context = context;
		this.zoomin = zoomin;
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
		redraw = true;
	}

	@Override
	public void released(MouseEvent e) {
		x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
		y1 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		pressed = false;
		changed = true;
		redraw = true;
	}

	@Override
	public void pressed(MouseEvent e) {
		x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
		y1 = (context.getHeight() / 2 - e.getY()) / context.getHeight();
		primary = e.isPrimaryButtonDown(); 
		pressed = true;
		redraw = true;
	}

	@Override
	public void update(long time) {
		if (pressed || changed) {
			MandelbrotMetadata oldMetadata = context.getMetadata();
			double[] t = oldMetadata.getTranslation().toArray();
			double[] r = oldMetadata.getRotation().toArray();
			double[] s = oldMetadata.getScale().toArray();
			double[] p = oldMetadata.getPoint().toArray();
			boolean j = oldMetadata.isJulia();
			double x = t[0];
			double y = t[1];
			double z = t[2];
			double a = r[2] * Math.PI / 180;
			double zs = (primary ? zoomin : !zoomin) ? 1 / context.getZoomSpeed() : context.getZoomSpeed();
			Number size = context.getInitialSize();
			x -= (zs - 1) * z * size.r() * (Math.cos(a) * x1 + Math.sin(a) * y1);
			y -= (zs - 1) * z * size.i() * (Math.cos(a) * y1 - Math.sin(a) * x1);
			z *= zs;
			MandelbrotMetadata newMetadata = new MandelbrotMetadata(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s, p, j);
			context.setView(newMetadata, pressed);
			changed = false;
		}
	}

	@Override
	public boolean isChanged() {
		boolean result = redraw;
		redraw = false;
		return result;
	}

	@Override
	public void draw(RendererGraphicsContext gc) {
		double dw = context.getWidth();
		double dh = context.getHeight();
		gc.clearRect(0, 0, (int)dw, (int)dh);
		if (pressed) {
			gc.setStroke(context.getRendererFactory().createColor(1, 1, 0, 1));
			double cx = dw / 2;
			double cy = dh / 2;
			int qx = (int) Math.rint(cx + x1 * dw);
			int qy = (int) Math.rint(cy - y1 * dh);
			gc.beginPath();
			gc.moveTo(qx - 4, qy - 4);
			gc.lineTo(qx + 4, qy + 4);
			gc.moveTo(qx - 4, qy + 4);
			gc.lineTo(qx + 4, qy - 4);
			gc.stroke();
		}
	}
}
