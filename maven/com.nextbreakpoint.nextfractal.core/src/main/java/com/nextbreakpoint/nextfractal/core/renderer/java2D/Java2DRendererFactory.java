/*
 * NextFractal 1.1.2
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
package com.nextbreakpoint.nextfractal.core.renderer.java2D;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.nextbreakpoint.nextfractal.core.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.core.renderer.RendererBuffer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererColor;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;

public class Java2DRendererFactory implements RendererFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createBuffer(int, int)
	 */
	@Override
	public RendererBuffer createBuffer(int width, int height) {
		return new Java2DRendererBuffer(width, height);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createGraphicsContext(java.lang.Object)
	 */
	@Override
	public RendererGraphicsContext createGraphicsContext(Object context) {
		return new Java2DRendererGraphicsContext((Graphics2D)context);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createTranslateAffine(double, double)
	 */
	@Override
	public RendererAffine createTranslateAffine(double x, double y) {
		return new Java2DRendererAffine(AffineTransform.getTranslateInstance(x, y));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createRotateAffine(double, double, double)
	 */
	@Override
	public RendererAffine createRotateAffine(double a, double centerX, double centerY) {
		return new Java2DRendererAffine(AffineTransform.getRotateInstance(a, centerX, centerY));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.renderer.RendererFactory#createScaleAffine(double, double)
	 */
	@Override
	public RendererAffine createScaleAffine(double x, double y) {
		return new Java2DRendererAffine(AffineTransform.getScaleInstance(x, y));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createAffine()
	 */
	@Override
	public RendererAffine createAffine() {
		return new Java2DRendererAffine(new AffineTransform());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createColor(int, int, int, int)
	 */
	@Override
	public RendererColor createColor(double red, double green, double blue, double opacity) {
		return new Java2DRendererColor(red, green, blue, opacity);
	}
}
