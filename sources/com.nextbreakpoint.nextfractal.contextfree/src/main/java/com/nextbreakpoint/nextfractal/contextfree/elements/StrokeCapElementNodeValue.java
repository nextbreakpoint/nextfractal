/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class StrokeCapElementNodeValue extends NodeValue<String> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public StrokeCapElementNodeValue(final String value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#getValueClone()
	 */
	@Override
	public String getValueClone() {
		return getValue();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#clone()
	 */
	@Override
	public StrokeCapElementNodeValue clone() {
		return new StrokeCapElementNodeValue(getValueClone());
	}
}
