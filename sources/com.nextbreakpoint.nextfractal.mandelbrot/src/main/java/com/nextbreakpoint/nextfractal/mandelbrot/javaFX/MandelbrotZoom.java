package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.scene.input.MouseEvent;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class MandelbrotZoom implements MandelbrotTool {
		private MandelbrotToolContext context;
		private volatile boolean pressed;
		private volatile boolean changed;
		private boolean zoomin;
		private double x1;
		private double y1;

		public MandelbrotZoom(MandelbrotToolContext context, boolean zoomin) {
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
			y1 = (e.getY() - context.getHeight() / 2) / context.getHeight();
			changed = true;
		}

		@Override
		public void released(MouseEvent e) {
			x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
			y1 = (e.getY() - context.getHeight() / 2) / context.getHeight();
			pressed = false;
			changed = true;
		}

		@Override
		public void pressed(MouseEvent e) {
			x1 = (e.getX() - context.getWidth() / 2) / context.getWidth();
			y1 = (e.getY() - context.getHeight() / 2) / context.getHeight();
//			zoomin = (e.isPrimaryButtonDown()) ? true : false;
			pressed = true;
		}

		@Override
		public void update(long time) {
			if (pressed || changed) {
				double[] t = context.getMandelbrotSession().getView().getTraslation();
				double[] r = context.getMandelbrotSession().getView().getRotation();
				double[] s = context.getMandelbrotSession().getView().getScale();
				double[] p = context.getMandelbrotSession().getView().getPoint();
				boolean j = context.getMandelbrotSession().getView().isJulia();
				double x = t[0];
				double y = t[1];
				double z = t[2];
				double a = r[2] * Math.PI / 180;
				double zs = zoomin ? 1 / context.getZoomSpeed() : context.getZoomSpeed();
				Number size = context.getInitialSize();
				x -= (zs - 1) * z * size.r() * (Math.cos(a) * x1 + Math.sin(a) * y1);
				y -= (zs - 1) * z * size.i() * (Math.cos(a) * y1 - Math.sin(a) * x1);
				z *= zs;
				MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s, p, j);
				context.getMandelbrotSession().setView(view, pressed);
				changed = false;
			}
		}
	}