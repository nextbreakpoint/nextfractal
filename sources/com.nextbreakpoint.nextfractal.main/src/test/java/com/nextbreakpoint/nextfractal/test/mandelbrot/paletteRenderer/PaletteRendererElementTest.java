/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer.DefaultRendererConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer.GrayGradientRendererConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.PaletteRendererExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.extension.PaletteRendererExtensionConfig;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.test.AbstractConfigurableExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class PaletteRendererElementTest extends AbstractConfigurableExtensionConfigElementTest<PaletteRendererExtensionConfig> {
	@Override
	protected Node createElementNode() {
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
