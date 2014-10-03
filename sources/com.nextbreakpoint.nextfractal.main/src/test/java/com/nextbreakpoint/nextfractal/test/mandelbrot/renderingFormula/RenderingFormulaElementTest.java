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
package com.nextbreakpoint.nextfractal.test.mandelbrot.renderingFormula;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.test.AbstractConfigurableExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.Z2Config;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.Z3Config;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class RenderingFormulaElementTest extends AbstractConfigurableExtensionConfigElementTest<RenderingFormulaExtensionConfig> {
	@Override
	protected Node createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<RenderingFormulaExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<RenderingFormulaExtensionConfig> value) {
				return new RenderingFormulaExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<RenderingFormulaExtensionConfig> createConfigElement(final ConfigurableExtensionReference<RenderingFormulaExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<RenderingFormulaExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<RenderingFormulaExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<RenderingFormulaExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<RenderingFormulaExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<RenderingFormulaExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<RenderingFormulaExtensionConfig>(MandelbrotRegistry.getInstance().getRenderingFormulaRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<RenderingFormulaExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<RenderingFormulaExtensionConfig> reference = new ConfigurableExtensionReference<RenderingFormulaExtensionConfig>("twister.mandelbrot.fractal.rendering.formula.z2", "Z2", new Z2Config());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<RenderingFormulaExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<RenderingFormulaExtensionConfig> reference = new ConfigurableExtensionReference<RenderingFormulaExtensionConfig>("twister.mandelbrot.fractal.rendering.formula.z3", "Z3", new Z3Config());
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
