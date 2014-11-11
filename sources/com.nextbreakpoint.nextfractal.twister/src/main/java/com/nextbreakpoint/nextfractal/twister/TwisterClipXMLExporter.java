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

import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class TwisterClipXMLExporter extends XMLExporter<TwisterClip> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final TwisterClip clip, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, "clip");
		exportProperties(clip, element, builder);
		return element;
	}

	/**
	 * @param clip
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final TwisterClip clip, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportSequenceList(clip, createProperty(builder, element, "sequenceList"), builder);
	}

	/**
	 * @param clip
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportSequenceList(final TwisterClip clip, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final TwisterSequenceXMLExporter sequenceExporter = new TwisterSequenceXMLExporter();
		for (int i = 0; i < clip.getSequenceCount(); i++) {
			final TwisterSequence sequence = clip.getSequence(i);
			element.appendChild(sequenceExporter.exportToElement(sequence, builder));
		}
	}
}
