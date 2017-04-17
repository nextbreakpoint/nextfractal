/*
 * NextFractal 2.0.1
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
package com.nextbreakpoint.nextfractal.contextfree.test;

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGRenderer;
import com.nextbreakpoint.nextfractal.contextfree.grammar.SimpleCanvas;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.geom.AffineTransform;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class V3ShapeTest extends AbstractBaseTest {
	public static final String RESOURCE_NAME = "/v3-shape-single-rule.cfdg";

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldParseSource() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		assertThat(cfdg, is(notNullValue()));
	}

	@Test
	public void shouldHaveTwoShapes() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		assertThat(cfdg.tryEncodeShapeName("Foo"), is(equalTo(4)));
		assertThat(cfdg.tryEncodeShapeName("CIRCLE"), is(equalTo(0)));
		assertThat(cfdg.tryEncodeShapeName("SQUARE"), is(equalTo(1)));
		assertThat(cfdg.tryEncodeShapeName("TRIANGLE"), is(equalTo(2)));
	}

	@Test
	public void shouldReloadRules() throws IOException {
		CFDG cfdg = parseSource(RESOURCE_NAME);
		assertThat(cfdg.getContents().getBody().size(), is(equalTo(2)));
		assertThat(cfdg.getRulesCount(), is(equalTo(1)));
		assertThat(cfdg.getRule(0).getWeight(), is(equalTo(1.0)));
		cfdg.rulesLoaded();
		assertThat(cfdg.getRule(0).getWeight(), is(equalTo(1.1)));
	}

	@Test
	public void shouldReturnScale() throws IOException {
		SimpleCanvas canvas = mock(SimpleCanvas.class);
		when(canvas.getWidth()).thenReturn(200);
		when(canvas.getHeight()).thenReturn(200);
		CFDG cfdg = parseSource(RESOURCE_NAME);
		cfdg.rulesLoaded();
		CFDGRenderer renderer = cfdg.renderer(200, 200, 1, 0, 0.1);
		assertThat(renderer, is(notNullValue()));
		double scale = renderer.run(canvas, false);
		assertThat(scale, is(equalTo(198.4)));
	}

	@Test
	public void shouldCallPrimitive() throws IOException {
		SimpleCanvas canvas = mock(SimpleCanvas.class);
		when(canvas.getWidth()).thenReturn(200);
		when(canvas.getHeight()).thenReturn(200);
		CFDG cfdg = parseSource(RESOURCE_NAME);
		cfdg.rulesLoaded();
		CFDGRenderer renderer = cfdg.renderer(200, 200, 1, 0, 0.1);
		assertThat(renderer, is(notNullValue()));
		renderer.run(canvas, false);
		AffineTransform transform = new AffineTransform();
		transform.translate(100.0, 100.0);
		transform.scale(198.4, 198.4);
		verify(canvas, times(1)).primitive(1, new double[] { 0, 0, 0, 1 }, transform);
	}
}
