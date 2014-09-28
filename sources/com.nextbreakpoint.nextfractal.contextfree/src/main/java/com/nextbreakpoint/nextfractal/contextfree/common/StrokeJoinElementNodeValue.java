/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.common;

import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class StrokeJoinElementNodeValue extends NodeValue<String> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public StrokeJoinElementNodeValue(final String value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeValue#getValueClone()
	 */
	@Override
	public String getValueClone() {
		return getValue();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeValue#clone()
	 */
	@Override
	public StrokeJoinElementNodeValue clone() {
		return new StrokeJoinElementNodeValue(getValueClone());
	}
}
