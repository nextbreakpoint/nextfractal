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

import com.nextbreakpoint.nextfractal.core.test.AbsractValueElementTest;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector4D;
import com.nextbreakpoint.nextfractal.twister.common.ViewElement;
import com.nextbreakpoint.nextfractal.twister.common.ViewElementNode;
import com.nextbreakpoint.nextfractal.twister.common.ViewElementXMLExporter;
import com.nextbreakpoint.nextfractal.twister.common.ViewElementXMLImporter;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class ViewElementTest extends AbsractValueElementTest<View, ViewElement> {
	@Override
	protected View getFirstValue() {
		return new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	}

	@Override
	protected View getSecondValue() {
		return new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 2, 0), new DoubleVector4D(0, 0, 0, 0));
	}

	@Override
	protected ViewElement createConfigElement(final View defaultValue) {
		final ViewElement configElement = new ViewElement(defaultValue);
		return configElement;
	}

	@Override
	protected NodeObject createElementNode() {
		return new ViewElementNode("value", getConfigElement()) {
		};
	}

	@Override
	protected ViewElementXMLImporter createXMLImporter() {
		return new ViewElementXMLImporter();
	}

	@Override
	protected ViewElementXMLExporter createXMLExporter() {
		return new ViewElementXMLExporter();
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
