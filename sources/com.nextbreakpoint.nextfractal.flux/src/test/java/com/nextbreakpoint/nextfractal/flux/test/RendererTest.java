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
package com.nextbreakpoint.nextfractal.flux.test;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.flux.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer;

public class RendererTest {
	@Test
	public void RendererStart() {
		DefaultThreadFactory threadFactory = new DefaultThreadFactory("Test", false, Thread.NORM_PRIORITY);
//		JavaFXRenderFactory renderFactory = new JavaFXRenderFactory();
		MandelbrotFractal rendererFractal = new MandelbrotFractal(null);
		Renderer renderer = new Renderer(threadFactory, rendererFractal, 100, 100);
		renderer.startRender(false, 0);
		renderer.joinRender();
		renderer.dispose();
	}
}