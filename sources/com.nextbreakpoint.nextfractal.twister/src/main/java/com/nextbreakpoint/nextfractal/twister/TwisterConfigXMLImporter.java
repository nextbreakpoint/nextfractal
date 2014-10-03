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
package com.nextbreakpoint.nextfractal.twister;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class TwisterConfigXMLImporter extends XMLImporter<TwisterConfig> {
	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	@Override
	public TwisterConfig importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, TwisterConfig.CLASS_ID);
		final TwisterConfig config = new TwisterConfig();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 3) {
			importProperties(config, propertyElements);
		}
		return config;
	}

	/**
	 * @param config
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final TwisterConfig config, final List<Element> propertyElements) throws XMLImportException {
		importBackground(config, propertyElements.get(0));
		importFrame(config, propertyElements.get(1));
		importEffect(config, propertyElements.get(2));
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importBackground(final TwisterConfig config, final Element element) throws XMLImportException {
		final List<Element> colorsElements = this.getElements(element, ColorElement.CLASS_ID);
		if (colorsElements.size() == 1) {
			config.setBackground(new ColorElementXMLImporter().importFromElement(colorsElements.get(0)).getValue());
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importFrame(final TwisterConfig config, final Element element) throws XMLImportException {
		final FrameConfigElementXMLImporter frameImporter = new FrameConfigElementXMLImporter();
		final List<Element> frameElements = this.getElements(element, FrameConfigElement.CLASS_ID);
		if (frameElements.size() == 1) {
			config.setFrameConfigElement(frameImporter.importFromElement(frameElements.get(0)));
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importEffect(final TwisterConfig config, final Element element) throws XMLImportException {
		final EffectConfigElementXMLImporter effectImporter = new EffectConfigElementXMLImporter();
		final List<Element> effectElements = this.getElements(element, EffectConfigElement.CLASS_ID);
		if (effectElements.size() == 1) {
			config.setEffectConfigElement(effectImporter.importFromElement(effectElements.get(0)));
		}
	}
}
