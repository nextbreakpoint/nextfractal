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
package com.nextbreakpoint.nextfractal.test.twister.image;

import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.extensions.image.BlackConfig;
import com.nextbreakpoint.nextfractal.twister.extensions.image.ColorConfig;
import com.nextbreakpoint.nextfractal.twister.image.ImageExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionConfig;

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
public class ImageElementTest extends AbstractConfigurableExtensionConfigElementTest<ImageExtensionConfig> {
	@Override
	protected Node createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<ImageExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<ImageExtensionConfig> value) {
				return new ImageExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<ImageExtensionConfig> createConfigElement(final ConfigurableExtensionReference<ImageExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<ImageExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<ImageExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<ImageExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<ImageExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<ImageExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<ImageExtensionConfig>(TwisterRegistry.getInstance().getImageRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<ImageExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<ImageExtensionConfig> reference = new ConfigurableExtensionReference<ImageExtensionConfig>("twister.frame.layer.image.black", "Black", new BlackConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<ImageExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<ImageExtensionConfig> reference = new ConfigurableExtensionReference<ImageExtensionConfig>("twister.frame.layer.image.color", "Color", new ColorConfig());
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
