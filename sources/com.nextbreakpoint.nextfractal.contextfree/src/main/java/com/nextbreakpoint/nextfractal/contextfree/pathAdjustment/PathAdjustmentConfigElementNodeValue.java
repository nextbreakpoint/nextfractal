/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathAdjustment;

import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;

public class PathAdjustmentConfigElementNodeValue extends NodeValue<PathAdjustmentConfigElement> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public PathAdjustmentConfigElementNodeValue(final PathAdjustmentConfigElement value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue#getValueClone()
	 */
	@Override
	public PathAdjustmentConfigElement getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue#clone()
	 */
	@Override
	public PathAdjustmentConfigElementNodeValue clone() {
		return new PathAdjustmentConfigElementNodeValue(getValueClone());
	}
}
