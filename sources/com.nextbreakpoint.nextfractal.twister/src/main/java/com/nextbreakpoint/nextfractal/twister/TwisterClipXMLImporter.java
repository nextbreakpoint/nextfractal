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

import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class TwisterClipXMLImporter extends XMLImporter<TwisterClip> {
	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	@Override
	public TwisterClip importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, "clip");
		final TwisterClip clip = new TwisterClip();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
			importProperties(clip, propertyElements);
		}
		return clip;
	}

	/**
	 * @param clip
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final TwisterClip clip, final List<Element> propertyElements) throws XMLImportException {
		importSequenceList(clip, propertyElements.get(0));
	}

	/**
	 * @param clip
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importSequenceList(final TwisterClip clip, final Element element) throws XMLImportException {
		final TwisterSequenceXMLImporter sequenceImporter = new TwisterSequenceXMLImporter();
		final List<Element> sequenceElements = this.getElements(element, "sequence");
		for (int i = 0; i < sequenceElements.size(); i++) {
			clip.addSequence(sequenceImporter.importFromElement(sequenceElements.get(i)));
		}
	}
}
