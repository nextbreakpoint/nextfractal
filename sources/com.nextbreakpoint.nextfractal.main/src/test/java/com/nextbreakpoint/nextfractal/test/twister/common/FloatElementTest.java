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

import com.nextbreakpoint.nextfractal.core.elements.FloatElement;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementNode;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.test.AbsractValueElementTest;

/**
 * @author Andrea Medeghini
 */
public class FloatElementTest extends AbsractValueElementTest<Float, FloatElement> {
	@Override
	protected Float getFirstValue() {
		return new Float(0);
	}

	@Override
	protected Float getSecondValue() {
		return new Float(1);
	}

	@Override
	protected FloatElement createConfigElement(final Float defaultValue) {
		final FloatElement configElement = new FloatElement(defaultValue);
		return configElement;
	}

	@Override
	protected NodeObject createElementNode() {
		return new FloatElementNode("value", getConfigElement()) {
		};
	}

	@Override
	protected FloatElementXMLImporter createXMLImporter() {
		return new FloatElementXMLImporter();
	}

	@Override
	protected FloatElementXMLExporter createXMLExporter() {
		return new FloatElementXMLExporter();
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
