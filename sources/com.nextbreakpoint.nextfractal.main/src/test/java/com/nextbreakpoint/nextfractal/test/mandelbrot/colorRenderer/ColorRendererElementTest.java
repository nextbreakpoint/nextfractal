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
package com.nextbreakpoint.nextfractal.test.mandelbrot.colorRenderer;

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
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.colorRenderer.COSConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.colorRenderer.SINConfig;

/**
 * @author Andrea Medeghini
 */
public class ColorRendererElementTest extends AbstractConfigurableExtensionConfigElementTest<ColorRendererExtensionConfig> {
	@Override
	protected NodeObject createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<ColorRendererExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<ColorRendererExtensionConfig> value) {
				return new ColorRendererExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<ColorRendererExtensionConfig> createConfigElement(final ConfigurableExtensionReference<ColorRendererExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<ColorRendererExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<ColorRendererExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<ColorRendererExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<ColorRendererExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<ColorRendererExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<ColorRendererExtensionConfig>(MandelbrotRegistry.getInstance().getColorRendererRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<ColorRendererExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<ColorRendererExtensionConfig> reference = new ConfigurableExtensionReference<ColorRendererExtensionConfig>("twister.mandelbrot.color.renderer.sin", "SIN", new SINConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<ColorRendererExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<ColorRendererExtensionConfig> reference = new ConfigurableExtensionReference<ColorRendererExtensionConfig>("twister.mandelbrot.color.renderer.cos", "COS", new COSConfig());
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
