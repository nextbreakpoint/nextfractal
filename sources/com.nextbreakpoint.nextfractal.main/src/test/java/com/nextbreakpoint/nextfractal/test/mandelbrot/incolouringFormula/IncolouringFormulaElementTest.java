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
package com.nextbreakpoint.nextfractal.test.mandelbrot.incolouringFormula;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.test.AbstractConfigurableExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.ModulusConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.PhaseConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class IncolouringFormulaElementTest extends AbstractConfigurableExtensionConfigElementTest<IncolouringFormulaExtensionConfig> {
	@Override
	protected NodeObject createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<IncolouringFormulaExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> value) {
				return new IncolouringFormulaExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<IncolouringFormulaExtensionConfig> createConfigElement(final ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<IncolouringFormulaExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<IncolouringFormulaExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<IncolouringFormulaExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<IncolouringFormulaExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<IncolouringFormulaExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<IncolouringFormulaExtensionConfig>(MandelbrotRegistry.getInstance().getIncolouringFormulaRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> reference = new ConfigurableExtensionReference<IncolouringFormulaExtensionConfig>("twister.mandelbrot.fractal.incolouring.formula.phase", "Z Phase", new PhaseConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> reference = new ConfigurableExtensionReference<IncolouringFormulaExtensionConfig>("twister.mandelbrot.fractal.incolouring.formula.modulus", "Z Modulus", new ModulusConfig());
		return reference;
	}

	@Override
	@Test
	public void testSetReference() {
		super.testSetReference();
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
}
