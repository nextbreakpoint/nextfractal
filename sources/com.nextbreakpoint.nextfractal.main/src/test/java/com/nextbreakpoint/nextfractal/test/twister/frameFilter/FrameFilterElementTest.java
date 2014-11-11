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
package com.nextbreakpoint.nextfractal.test.twister.frameFilter;

import org.junit.Test;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.test.AbstractConfigurableExtensionConfigElementTest;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensions.frameFilter.BlackConfig;
import com.nextbreakpoint.nextfractal.twister.extensions.frameFilter.ColorConfig;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterExtensionReferenceNodeValue;

/**
 * @author Andrea Medeghini
 */
public class FrameFilterElementTest extends AbstractConfigurableExtensionConfigElementTest<FrameFilterExtensionConfig> {
	@Override
	protected NodeObject createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<FrameFilterExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<FrameFilterExtensionConfig> value) {
				return new FrameFilterExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<FrameFilterExtensionConfig> createConfigElement(final ConfigurableExtensionReference<FrameFilterExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<FrameFilterExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<FrameFilterExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<FrameFilterExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<FrameFilterExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<FrameFilterExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<FrameFilterExtensionConfig>(TwisterRegistry.getInstance().getFrameFilterRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<FrameFilterExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<FrameFilterExtensionConfig> reference = new ConfigurableExtensionReference<FrameFilterExtensionConfig>("twister.frame.filter.black", "Black", new BlackConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<FrameFilterExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<FrameFilterExtensionConfig> reference = new ConfigurableExtensionReference<FrameFilterExtensionConfig>("twister.frame.filter.color", "Color", new ColorConfig());
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
