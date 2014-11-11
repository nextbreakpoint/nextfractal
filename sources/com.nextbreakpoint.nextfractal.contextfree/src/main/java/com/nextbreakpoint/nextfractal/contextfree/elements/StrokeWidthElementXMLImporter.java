/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElementXMLImporter extends ValueConfigElementXMLImporter<Float, StrokeWidthElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected Float parseValue(final String value) {
		return Float.valueOf(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected StrokeWidthElement createDefaultConfigElement() {
		return new StrokeWidthElement(1f);
	}
}
