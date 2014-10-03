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
package com.nextbreakpoint.nextfractal.test.twister.layerFilter;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.test.AbstractConfigurableExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.BlackConfig;
import com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.ColorConfig;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.twister.layerFilter.extension.LayerFilterExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class LayerFilterElementTest extends AbstractConfigurableExtensionConfigElementTest<LayerFilterExtensionConfig> {
	@Override
	protected Node createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<LayerFilterExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<LayerFilterExtensionConfig> value) {
				return new LayerFilterExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<LayerFilterExtensionConfig> createConfigElement(final ConfigurableExtensionReference<LayerFilterExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<LayerFilterExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<LayerFilterExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<LayerFilterExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<LayerFilterExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<LayerFilterExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<LayerFilterExtensionConfig>(TwisterRegistry.getInstance().getLayerFilterRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<LayerFilterExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<LayerFilterExtensionConfig> reference = new ConfigurableExtensionReference<LayerFilterExtensionConfig>("twister.frame.layer.filter.black", "Black", new BlackConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<LayerFilterExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<LayerFilterExtensionConfig> reference = new ConfigurableExtensionReference<LayerFilterExtensionConfig>("twister.frame.layer.filter.color", "Color", new ColorConfig());
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
