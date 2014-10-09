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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ComplexElement;
import com.nextbreakpoint.nextfractal.core.common.ComplexElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ThresholdElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ThresholdElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractRenderingFormulaConfigXMLImporter<T extends RenderingFormulaExtensionConfig> extends XMLImporter<T> {
	/**
	 * @return
	 */
	protected abstract String getConfigElementClassId();

	/**
	 * @return
	 */
	protected abstract T createExtensionConfig();

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public T importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, this.getConfigElementClassId());
		final T config = this.createExtensionConfig();
		final List<Element> propertyElements = getProperties(element);
		importProperties(config, propertyElements);
		return config;
	}

	/**
	 * @return
	 */
	protected int getPropertiesSize() {
		return 4;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	protected void importProperties(final T config, final List<Element> propertyElements) throws XMLImportException {
		this.importIterations(config, propertyElements.get(0));
		this.importThreshold(config, propertyElements.get(1));
		this.importCenter(config, propertyElements.get(2));
		this.importScale(config, propertyElements.get(3));
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importIterations(final T config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, IterationsElement.CLASS_ID);
		if (elements.size() == 1) {
			config.setIterations(new IterationsElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importThreshold(final T config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, ThresholdElement.CLASS_ID);
		if (elements.size() == 1) {
			config.setThreshold(new ThresholdElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importCenter(final T config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, ComplexElement.CLASS_ID);
		if (elements.size() == 1) {
			config.setCenter(new ComplexElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importScale(final T config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, ComplexElement.CLASS_ID);
		if (elements.size() == 1) {
			config.setScale(new ComplexElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}
}
