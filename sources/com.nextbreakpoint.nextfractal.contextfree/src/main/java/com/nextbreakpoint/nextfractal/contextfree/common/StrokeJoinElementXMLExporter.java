/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.common;

import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeJoinElementXMLExporter extends ValueConfigElementXMLExporter<String, StrokeJoinElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter#formatValue(java.io.Serializable)
	 */
	@Override
	protected String formatValue(final String value) {
		return value;
	}
}
