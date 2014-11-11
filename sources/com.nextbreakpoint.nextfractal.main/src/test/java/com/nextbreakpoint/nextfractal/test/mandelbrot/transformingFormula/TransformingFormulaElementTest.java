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
package com.nextbreakpoint.nextfractal.test.mandelbrot.transformingFormula;

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
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.transformingFormula.YConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.transformingFormula.ZConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class TransformingFormulaElementTest extends AbstractConfigurableExtensionConfigElementTest<TransformingFormulaExtensionConfig> {
	@Override
	protected NodeObject createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<TransformingFormulaExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<TransformingFormulaExtensionConfig> value) {
				return new TransformingFormulaExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<TransformingFormulaExtensionConfig> createConfigElement(final ConfigurableExtensionReference<TransformingFormulaExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<TransformingFormulaExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<TransformingFormulaExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<TransformingFormulaExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<TransformingFormulaExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<TransformingFormulaExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<TransformingFormulaExtensionConfig>(MandelbrotRegistry.getInstance().getTransformingFormulaRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<TransformingFormulaExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<TransformingFormulaExtensionConfig> reference = new ConfigurableExtensionReference<TransformingFormulaExtensionConfig>("twister.mandelbrot.fractal.transforming.formula.z", "Z", new ZConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<TransformingFormulaExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<TransformingFormulaExtensionConfig> reference = new ConfigurableExtensionReference<TransformingFormulaExtensionConfig>("twister.mandelbrot.fractal.transforming.formula.y", "Y", new YConfig());
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
