/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.common;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class FillRuleElement extends ValueConfigElement<String> {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "FillRule";

	/**
	 * Constructs a new element.
	 */
	public FillRuleElement(String defaultValue) {
		super(FillRuleElement.CLASS_ID, defaultValue);
	}

	/**
	 * @return
	 */
	@Override
	public FillRuleElement clone() {
		return new FillRuleElement(getValue());
	}

	/**
	 *
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		FillRuleElement element = (FillRuleElement) source;
		setValue(element.getValue());
	}
}
