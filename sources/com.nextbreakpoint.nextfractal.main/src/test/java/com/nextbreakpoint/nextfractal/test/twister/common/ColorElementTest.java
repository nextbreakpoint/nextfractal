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
package com.nextbreakpoint.nextfractal.test.twister.common;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementNode;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.test.AbsractValueElementTest;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;

/**
 * @author Andrea Medeghini
 */
public class ColorElementTest extends AbsractValueElementTest<Color32bit, ColorElement> {
	@Override
	protected Color32bit getFirstValue() {
		return Color32bit.BLACK;
	}

	@Override
	protected Color32bit getSecondValue() {
		return Color32bit.WHITE;
	}

	@Override
	protected ColorElement createConfigElement(final Color32bit defaultValue) {
		final ColorElement configElement = new ColorElement(defaultValue);
		return configElement;
	}

	@Override
	protected NodeObject createElementNode() {
		return new ColorElementNode("value", getConfigElement()) {
		};
	}

	@Override
	protected ColorElementXMLImporter createXMLImporter() {
		return new ColorElementXMLImporter();
	}

	@Override
	protected ColorElementXMLExporter createXMLExporter() {
		return new ColorElementXMLExporter();
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
