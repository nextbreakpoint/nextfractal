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
package com.nextbreakpoint.nextfractal.test.twister.effect;

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
import com.nextbreakpoint.nextfractal.twister.effect.EffectExtensionReferenceNodeValue;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensions.effect.FireConfig;
import com.nextbreakpoint.nextfractal.twister.extensions.effect.WaterConfig;

/**
 * @author Andrea Medeghini
 */
public class EffectElementTest extends AbstractConfigurableExtensionConfigElementTest<EffectExtensionConfig> {
	@Override
	protected Node createElementNode() {
		return new ConfigurableExtensionReferenceElementNode<EffectExtensionConfig>("reference", getConfigElement()) {
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<EffectExtensionConfig> value) {
				return new EffectExtensionReferenceNodeValue(value);
			}
		};
	}

	@Override
	protected ConfigurableExtensionReferenceElement<EffectExtensionConfig> createConfigElement(final ConfigurableExtensionReference<EffectExtensionConfig> defaultValue) {
		final ConfigurableExtensionReferenceElement<EffectExtensionConfig> configElement = new ConfigurableExtensionReferenceElement<EffectExtensionConfig>();
		configElement.setReference(defaultValue);
		return configElement;
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLExporter<EffectExtensionConfig> createXMLExporter() {
		return new ConfigurableExtensionReferenceElementXMLExporter<EffectExtensionConfig>();
	}

	@Override
	protected ConfigurableExtensionReferenceElementXMLImporter<EffectExtensionConfig> createXMLImporter() {
		return new ConfigurableExtensionReferenceElementXMLImporter<EffectExtensionConfig>(TwisterRegistry.getInstance().getEffectRegistry());
	}

	@Override
	protected ConfigurableExtensionReference<EffectExtensionConfig> getFirstReference() {
		final ConfigurableExtensionReference<EffectExtensionConfig> reference = new ConfigurableExtensionReference<EffectExtensionConfig>("twister.effect.fire", "Fire", new FireConfig());
		return reference;
	}

	@Override
	protected ConfigurableExtensionReference<EffectExtensionConfig> getSecondReference() {
		final ConfigurableExtensionReference<EffectExtensionConfig> reference = new ConfigurableExtensionReference<EffectExtensionConfig>("twister.effect.water", "Water", new WaterConfig());
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
