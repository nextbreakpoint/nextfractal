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

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.test.AbsractValueElementTest;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.ThresholdElement;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.ThresholdElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.ThresholdElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.ThresholdElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ThresholdElementTest extends AbsractValueElementTest<Double, ThresholdElement> {
	@Override
	protected Double getFirstValue() {
		return new Double(20);
	}

	@Override
	protected Double getSecondValue() {
		return new Double(40);
	}

	@Override
	protected ThresholdElement createConfigElement(final Double defaultValue) {
		final ThresholdElement configElement = new ThresholdElement(defaultValue);
		configElement.setMaximum(100d);
		configElement.setMinimum(1d);
		configElement.setStep(0.1);
		return configElement;
	}

	@Override
	protected NodeObject createElementNode() {
		return new ThresholdElementNode("value", getConfigElement()) {
		};
	}

	@Override
	protected ThresholdElementXMLImporter createXMLImporter() {
		return new ThresholdElementXMLImporter();
	}

	@Override
	protected ThresholdElementXMLExporter createXMLExporter() {
		return new ThresholdElementXMLExporter();
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
