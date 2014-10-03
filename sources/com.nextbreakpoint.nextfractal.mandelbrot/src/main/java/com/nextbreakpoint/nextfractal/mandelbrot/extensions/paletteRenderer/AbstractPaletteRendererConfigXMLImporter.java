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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.extension.PaletteRendererExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractPaletteRendererConfigXMLImporter<T extends PaletteRendererExtensionConfig> extends XMLImporter<T> {
	/**
	 * @return
	 */
	protected abstract String getConfigElementClassId();

	/**
	 * @return
	 */
	protected abstract T createExtensionConfig();

	/**
	 * @return
	 */
	protected abstract int getPropertiesSize();

	/**
	 * @param config
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected abstract void importProperties(T config, List<Element> propertyElements) throws XMLImportException;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public T importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, this.getConfigElementClassId());
		final T config = this.createExtensionConfig();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == this.getPropertiesSize()) {
			this.importProperties(config, propertyElements);
		}
		return config;
	}
}
