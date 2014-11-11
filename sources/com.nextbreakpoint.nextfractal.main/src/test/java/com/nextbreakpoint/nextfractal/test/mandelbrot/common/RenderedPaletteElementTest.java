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
package com.nextbreakpoint.nextfractal.test.mandelbrot.common;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.test.AbsractValueElementTest;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.RenderedPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.RenderedPaletteElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.RenderedPaletteElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.RenderedPaletteElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPaletteParam;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteElementTest extends AbsractValueElementTest<RenderedPalette, RenderedPaletteElement> {
	@Override
	protected RenderedPalette getFirstValue() {
		return new RenderedPalette(new RenderedPaletteParam[] { new DefaultRenderedPaletteParam(new int[] { 0xFF000000, 0xFFFFFFFF }, 100) });
	}

	@Override
	protected RenderedPalette getSecondValue() {
		return new RenderedPalette(new RenderedPaletteParam[] { new DefaultRenderedPaletteParam(new int[] { 0xFF000000, 0xFFFFFFFF }, 100) });
	}

	@Override
	protected RenderedPaletteElement createConfigElement(final RenderedPalette defaultValue) {
		final RenderedPaletteElement configElement = new RenderedPaletteElement(defaultValue);
		return configElement;
	}

	@Override
	protected NodeObject createElementNode() {
		return new RenderedPaletteElementNode("value", getConfigElement()) {
		};
	}

	@Override
	protected RenderedPaletteElementXMLImporter createXMLImporter() {
		return new RenderedPaletteElementXMLImporter();
	}

	@Override
	protected RenderedPaletteElementXMLExporter createXMLExporter() {
		return new RenderedPaletteElementXMLExporter();
	}

	@Override
	@Test
	public void testSetValue() {
		super.testSetValue();
	}

	@Override
	@Test
	public void testNode() {
		super.testNode();
	}

	@Override
	@Test
	public void testClone() {
		super.testClone();
	}

	@Override
	@Test
	public void testSerialization() {
		super.testSerialization();
	}

	@Override
	@Test
	public void testXML() {
		super.testXML();
	}

	@Override
	@Test
	public void testActions() {
		super.testActions();
	}
}
