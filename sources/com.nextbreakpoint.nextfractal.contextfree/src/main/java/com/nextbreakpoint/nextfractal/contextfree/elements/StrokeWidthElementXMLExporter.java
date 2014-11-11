/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElementXMLExporter extends ValueConfigElementXMLExporter<Float, StrokeWidthElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLExporter#formatValue(java.io.Serializable)
	 */
	@Override
	protected String formatValue(final Float value) {
		return Float.toString(value);
	}
}
