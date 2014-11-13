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

import com.nextbreakpoint.nextfractal.core.elements.LongElement;
import com.nextbreakpoint.nextfractal.core.elements.LongElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.ActionXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class TwisterSequenceXMLImporter extends XMLImporter<TwisterSequence> {
	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	@Override
	public TwisterSequence importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, "sequence");
		final TwisterSequence sequence = new TwisterSequence();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 4) {
			importProperties(sequence, propertyElements);
		}
		return sequence;
	}

	/**
	 * @param sequence
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final TwisterSequence sequence, final List<Element> propertyElements) throws XMLImportException {
		importDuration(sequence, propertyElements.get(0));
		importInitialConfig(sequence, propertyElements.get(1));
		importFinalConfig(sequence, propertyElements.get(2));
		importActionList(sequence, propertyElements.get(3));
	}

	/**
	 * @param sequence
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importDuration(final TwisterSequence sequence, final Element element) throws XMLImportException {
		final LongElementXMLImporter elementImporter = new LongElementXMLImporter();
		final List<Element> clipElements = this.getElements(element, LongElement.CLASS_ID);
		if (clipElements.size() == 1) {
			sequence.setDuration(elementImporter.importFromElement(clipElements.get(0)).getValue());
		}
	}

	/**
	 * @param sequence
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importInitialConfig(final TwisterSequence sequence, final Element element) throws XMLImportException {
		final TwisterConfigXMLImporter configImporter = new TwisterConfigXMLImporter();
		final List<Element> clipElements = this.getElements(element, TwisterConfig.CLASS_ID);
		if (clipElements.size() == 1) {
			sequence.setInitialConfig(configImporter.importFromElement(clipElements.get(0)));
		}
	}

	/**
	 * @param sequence
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importFinalConfig(final TwisterSequence sequence, final Element element) throws XMLImportException {
		final TwisterConfigXMLImporter configImporter = new TwisterConfigXMLImporter();
		final List<Element> clipElements = this.getElements(element, TwisterConfig.CLASS_ID);
		if (clipElements.size() == 1) {
			sequence.setFinalConfig(configImporter.importFromElement(clipElements.get(0)));
		}
	}

	/**
	 * @param sequence
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importActionList(final TwisterSequence sequence, final Element element) throws XMLImportException {
		final ActionXMLImporter actionImporter = new ActionXMLImporter();
		final List<Element> sequenceElements = this.getElements(element, "action");
		for (int i = 0; i < sequenceElements.size(); i++) {
			sequence.addAction(new NodeAction(actionImporter.importFromElement(sequenceElements.get(i))));
		}
	}
}
