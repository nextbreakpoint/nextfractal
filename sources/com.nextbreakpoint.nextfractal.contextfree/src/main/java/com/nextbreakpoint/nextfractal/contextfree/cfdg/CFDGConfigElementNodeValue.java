/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.cfdg;

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;

public class CFDGConfigElementNodeValue extends NodeValue<CFDGConfigElement> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public CFDGConfigElementNodeValue(final CFDGConfigElement value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue#getValueClone()
	 */
	@Override
	public CFDGConfigElement getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue#clone()
	 */
	@Override
	public CFDGConfigElementNodeValue clone() {
		return new CFDGConfigElementNodeValue(getValueClone());
	}
}
