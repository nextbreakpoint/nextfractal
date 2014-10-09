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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class UniversalTrueColorConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<UniversalTrueColorConfig> createXMLImporter() {
		return new UniversalTrueColorConfigXMLImporter();
	}

	private class UniversalTrueColorConfigXMLImporter extends AbstractIncolouringFormulaConfigXMLImporter<UniversalTrueColorConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringFormulaConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected UniversalTrueColorConfig createExtensionConfig() {
			return new UniversalTrueColorConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringFormulaConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "UniversalTrueColorConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringFormulaConfigXMLImporter#getPropertiesSize()
		 */
		@Override
		protected int getPropertiesSize() {
			return 1;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractIncolouringFormulaConfigXMLImporter#importProperties(com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig, java.util.List)
		 */
		@Override
		protected void importProperties(final UniversalTrueColorConfig config, final List<Element> propertyElements) throws XMLImportException {
			importColorRenderers(config, propertyElements.get(0));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importColorRenderers(final UniversalTrueColorConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, ColorRendererConfigElement.CLASS_ID);
			if (elements.size() == 4) {
				config.getColorRendererElement(0).setReference(new ColorRendererConfigElementXMLImporter().importFromElement(elements.get(0)).getReference());
				config.getColorRendererElement(1).setReference(new ColorRendererConfigElementXMLImporter().importFromElement(elements.get(1)).getReference());
				config.getColorRendererElement(2).setReference(new ColorRendererConfigElementXMLImporter().importFromElement(elements.get(2)).getReference());
				config.getColorRendererElement(3).setReference(new ColorRendererConfigElementXMLImporter().importFromElement(elements.get(3)).getReference());
			}
		}
	}
}
