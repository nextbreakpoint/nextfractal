/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.core.runtime.common.ValueConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementXMLImporter extends ValueConfigElementXMLImporter<String, StrokeCapElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected String parseValue(final String value) {
		return String.valueOf(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected StrokeCapElement createDefaultConfigElement() {
		return new StrokeCapElement("miter");
	}
}
