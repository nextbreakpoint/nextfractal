/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.common;

import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class FillRuleElementXMLExporter extends ValueConfigElementXMLExporter<String, FillRuleElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter#formatValue(java.io.Serializable)
	 */
	@Override
	protected String formatValue(final String value) {
		return value;
	}
}
