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

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class TwisterConfigXMLExporter extends XMLExporter<TwisterConfig> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final TwisterConfig config, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, TwisterConfig.CLASS_ID);
		exportProperties(config, element, builder);
		return element;
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final TwisterConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportBackground(config, createProperty(builder, element, "background"), builder);
		exportFrame(config, createProperty(builder, element, "frame"), builder);
		exportEffect(config, createProperty(builder, element, "effect"), builder);
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportBackground(final TwisterConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ColorElementXMLExporter().exportToElement(config.getBackgroundElement(), builder));
	}

	/**
	 * @param config
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportFrame(final TwisterConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final FrameConfigElementXMLExporter frameExporter = new FrameConfigElementXMLExporter();
		final FrameConfigElement frame = config.getFrameConfigElement();
		element.appendChild(frameExporter.exportToElement(frame, builder));
	}

	/**
	 * @param config
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportEffect(final TwisterConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final EffectConfigElementXMLExporter effectExporter = new EffectConfigElementXMLExporter();
		final EffectConfigElement effect = config.getEffectConfigElement();
		element.appendChild(effectExporter.exportToElement(effect, builder));
	}
}
