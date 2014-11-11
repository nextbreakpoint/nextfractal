/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;

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
