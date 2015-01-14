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
package com.nextbreakpoint.nextfractal.test;

import org.junit.Assert;
import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.Renderer;

public class RendererTest {
	@Test
	public void RendererStart() {
		DefaultThreadFactory threadFactory = new DefaultThreadFactory("Test", false, Thread.MIN_PRIORITY);
		Renderer renderer = new Renderer(threadFactory, 100, 100);
		try {
			TestOrbit orbit = new TestOrbit();
			TestColor color = new TestColor();
			Scope scope = new Scope();
			orbit.setScope(scope);
			color.setScope(scope);
			renderer.setOrbit(orbit);
			renderer.setColor(color);
			renderer.startRender(false);
			renderer.joinRender();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} finally {
			renderer.dispose();
		}
	}
	
	private class TestOrbit extends Orbit {
		@Override
		public void init() {
		}
		
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