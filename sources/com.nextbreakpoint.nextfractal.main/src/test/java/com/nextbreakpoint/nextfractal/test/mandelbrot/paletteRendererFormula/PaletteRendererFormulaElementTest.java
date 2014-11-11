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
package com.nextbreakpoint.nextfractal.test.mandelbrot.paletteRendererFormula;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.test.AbstractExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.PaletteRendererFormulaExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class PaletteRendererFormulaElementTest extends AbstractExtensionConfigElementTest {
	@Override
	protected NodeObject createElementNode() {
		return new ExtensionReferenceElementNode("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ExtensionReference value) {
				return new PaletteRendererFormulaExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ExtensionReferenceElement createConfigElement(final ExtensionReference defaultValue) {
		final ExtensionReferenceElement configElement = new ExtensionReferenceElement();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ExtensionReferenceElementXMLExporter createXMLExporter() {
		return new ExtensionReferenceElementXMLExporter();
	}

	@Override
	protected ExtensionReferenceElementXMLImporter createXMLImporter() {
		return new ExtensionReferenceElementXMLImporter(MandelbrotRegistry.getInstance().getPaletteRendererFormulaRegistry());
	}

	@Override
	protected ExtensionReference getFirstReference() {
		final ExtensionReference reference = new ExtensionReference("twister.mandelbrot.palette.renderer.formula.lin", "LIN");
		return reference;
	}

	@Override
	protected ExtensionReference getSecondReference() {
		final ExtensionReference reference = new ExtensionReference("twister.mandelbrot.palette.renderer.formula.log", "LOG");
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
