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

import com.nextbreakpoint.nextfractal.core.elements.LongElement;
import com.nextbreakpoint.nextfractal.core.elements.LongElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.ActionXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class TwisterSequenceXMLExporter extends XMLExporter<TwisterSequence> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final TwisterSequence sequence, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, "sequence");
		exportProperties(sequence, element, builder);
		return element;
	}

	/**
	 * @param sequence
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final TwisterSequence sequence, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportDuration(sequence, createProperty(builder, element, "duration"), builder);
		exportInitialConfig(sequence, createProperty(builder, element, "initialConfig"), builder);
		exportFinalConfig(sequence, createProperty(builder, element, "finalConfig"), builder);
		exportActionList(sequence, createProperty(builder, element, "actionList"), builder);
	}

	/**
	 * @param sequence
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportDuration(final TwisterSequence sequence, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final LongElementXMLExporter elementExporter = new LongElementXMLExporter();
		element.appendChild(elementExporter.exportToElement(new LongElement(sequence.getDuration()), builder));
	}

	/**
	 * @param sequence
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportInitialConfig(final TwisterSequence sequence, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final TwisterConfigXMLExporter configExporter = new TwisterConfigXMLExporter();
		if (sequence.getInitialConfig() != null) {
			element.appendChild(configExporter.exportToElement(sequence.getInitialConfig(), builder));
		}
	}

	/**
	 * @param sequence
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportFinalConfig(final TwisterSequence sequence, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final TwisterConfigXMLExporter configExporter = new TwisterConfigXMLExporter();
		if (sequence.getFinalConfig() != null) {
			element.appendChild(configExporter.exportToElement(sequence.getFinalConfig(), builder));
		}
	}

	/**
	 * @param sequence
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportActionList(final TwisterSequence sequence, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final ActionXMLExporter actionExporter = new ActionXMLExporter();
		for (int i = 0; i < sequence.getActionCount(); i++) {
			final NodeAction action = sequence.getAction(i);
			element.appendChild(actionExporter.exportToElement(action.toActionValue(), builder));
		}
	}
}
