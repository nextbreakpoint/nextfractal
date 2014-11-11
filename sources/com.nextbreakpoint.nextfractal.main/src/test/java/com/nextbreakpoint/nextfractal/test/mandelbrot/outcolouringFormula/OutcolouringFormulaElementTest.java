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
package com.nextbreakpoint.nextfractal.test.mandelbrot.outcolouringFormula;

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
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.ModulusConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.PhaseConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class OutcolouringFormulaElementTest extends AbstractConfigurableExtensionConfigElementTest<OutcolouringFormulaExtensionConfig> {
	@Override
	protected NodeObject createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<OutcolouringFormulaExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> value) {
				return new OutcolouringFormulaExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<OutcolouringFormulaExtensionConfig> createConfigElement(final ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<OutcolouringFormulaExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<OutcolouringFormulaExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<OutcolouringFormulaExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<OutcolouringFormulaExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<OutcolouringFormulaExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<OutcolouringFormulaExtensionConfig>(MandelbrotRegistry.getInstance().getOutcolouringFormulaRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> reference = new ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig>("twister.mandelbrot.fractal.outcolouring.formula.phase", "Z Phase", new PhaseConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig> reference = new ConfigurableExtensionReference<OutcolouringFormulaExtensionConfig>("twister.mandelbrot.fractal.outcolouring.formula.modulus", "Z Modulus", new ModulusConfig());
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
