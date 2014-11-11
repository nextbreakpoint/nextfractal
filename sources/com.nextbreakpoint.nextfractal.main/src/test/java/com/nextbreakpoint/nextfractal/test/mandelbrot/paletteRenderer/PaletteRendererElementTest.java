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
package com.nextbreakpoint.nextfractal.test.mandelbrot.paletteRenderer;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.test.AbstractConfigurableExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer.DefaultRendererConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer.GrayGradientRendererConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.PaletteRendererExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class PaletteRendererElementTest extends AbstractConfigurableExtensionConfigElementTest<PaletteRendererExtensionConfig> {
	@Override
	protected NodeObject createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<PaletteRendererExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<PaletteRendererExtensionConfig> value) {
				return new PaletteRendererExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<PaletteRendererExtensionConfig> createConfigElement(final ConfigurableExtensionReference<PaletteRendererExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<PaletteRendererExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<PaletteRendererExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<PaletteRendererExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<PaletteRendererExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<PaletteRendererExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<PaletteRendererExtensionConfig>(MandelbrotRegistry.getInstance().getPaletteRendererRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<PaletteRendererExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<PaletteRendererExtensionConfig> reference = new ConfigurableExtensionReference<PaletteRendererExtensionConfig>("twister.mandelbrot.palette.renderer.default", "Default", new DefaultRendererConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<PaletteRendererExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<PaletteRendererExtensionConfig> reference = new ConfigurableExtensionReference<PaletteRendererExtensionConfig>("twister.mandelbrot.palette.renderer.grayGradient", "Gray Gradient", new GrayGradientRendererConfig());
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
