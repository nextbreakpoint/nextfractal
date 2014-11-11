/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeJoinElementXMLImporter extends ValueConfigElementXMLImporter<String, StrokeJoinElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected String parseValue(final String value) {
		return String.valueOf(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected StrokeJoinElement createDefaultConfigElement() {
		return new StrokeJoinElement("butt");
	}
}
