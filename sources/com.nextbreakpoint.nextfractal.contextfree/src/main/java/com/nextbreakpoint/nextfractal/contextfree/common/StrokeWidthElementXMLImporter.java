/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.common;

import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElementXMLImporter extends ValueConfigElementXMLImporter<Float, StrokeWidthElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected Float parseValue(final String value) {
		return Float.valueOf(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected StrokeWidthElement createDefaultConfigElement() {
		return new StrokeWidthElement(1f);
	}
}
