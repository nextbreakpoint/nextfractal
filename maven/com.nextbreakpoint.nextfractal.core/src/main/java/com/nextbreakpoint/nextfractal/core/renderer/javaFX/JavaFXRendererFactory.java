/*
 * NextFractal 1.0
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
package com.nextbreakpoint.nextfractal.core.renderer.javaFX;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.core.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.core.renderer.RendererBuffer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererColor;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;

public class JavaFXRendererFactory implements RendererFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createBuffer(int, int)
	 */
	@Override
	public RendererBuffer createBuffer(int width, int height) {
		return new JavaFXRendererBuffer(width, height);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createGraphicsContext(java.lang.Object)
	 */
	@Override
	public RendererGraphicsContext createGraphicsContext(Object context) {
		return new JavaFXRendererGraphicsContext((GraphicsContext)context);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createTranslateAffine(double, double)
	 */
	@Override
	public RendererAffine createTranslateAffine(double x, double y) {
		return new JavaFXRendererAffine(new Affine(Affine.translate(x, y)));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createRotateAffine(double, double, double)
	 */
	@Override
	public RendererAffine createRotateAffine(double a, double centerX, double centerY) {
		return new JavaFXRendererAffine(new Affine(Affine.rotate(a, centerX, centerY)));
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createAffine()
	 */
	@Override
	public RendererAffine createAffine() {
		return new JavaFXRendererAffine(new Affine());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererFactory.twister.renderer.RenderFactory#createColor(int, int, int, int)
	 */
	@Override
	public RendererColor createColor(double red, double green, double blue, double opacity) {
		return new JavaFXRendererColor(red, green, blue, opacity);
	}
}
