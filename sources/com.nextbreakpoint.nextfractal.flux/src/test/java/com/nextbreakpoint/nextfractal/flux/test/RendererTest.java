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

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.flux.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Fractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public class RendererTest {
	@Test
	public void RendererStart() {
		DefaultThreadFactory threadFactory = new DefaultThreadFactory("Test", false, Thread.MIN_PRIORITY);
		Renderer renderer = new Renderer(threadFactory, 100, 100);
		try {
			RendererFractal rendererFractal = new RendererFractal(new TestFractal());
			renderer.setFractal(rendererFractal);
			renderer.startRender(false, 0);
			renderer.joinRender();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} finally {
			renderer.dispose();
		}
	}
	
	private class TestFractal extends Fractal {
		@Override
		protected Orbit createOrbit() {
			return new TestOrbit();
		}

		@Override
		protected Color createColor() {
			return new TestColor();
		}

		@Override
		public void renderOrbit() {
		}

		@Override
		public void renderColor() {
		}
	}
	
	private class TestOrbit extends Orbit {
		@Override
		public void render() {
		}
	}
	
	private class TestColor extends Color {
		@Override
		public void render() {
		}
	}
}