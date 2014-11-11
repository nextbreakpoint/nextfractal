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

import com.nextbreakpoint.nextfractal.core.elements.DoubleElement;
import com.nextbreakpoint.nextfractal.core.elements.DoubleElementNode;
import com.nextbreakpoint.nextfractal.core.elements.DoubleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.DoubleElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.test.AbsractValueElementTest;

/**
 * @author Andrea Medeghini
 */
public class DoubleElementTest extends AbsractValueElementTest<Double, DoubleElement> {
	@Override
	protected Double getFirstValue() {
		return new Double(0);
	}

	@Override
	protected Double getSecondValue() {
		return new Double(1);
	}

	@Override
	protected DoubleElement createConfigElement(final Double defaultValue) {
		final DoubleElement configElement = new DoubleElement(defaultValue);
		return configElement;
	}

	@Override
	protected NodeObject createElementNode() {
		return new DoubleElementNode("value", getConfigElement()) {
		};
	}

	@Override
	protected DoubleElementXMLImporter createXMLImporter() {
		return new DoubleElementXMLImporter();
	}

	@Override
	protected DoubleElementXMLExporter createXMLExporter() {
		return new DoubleElementXMLExporter();
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
