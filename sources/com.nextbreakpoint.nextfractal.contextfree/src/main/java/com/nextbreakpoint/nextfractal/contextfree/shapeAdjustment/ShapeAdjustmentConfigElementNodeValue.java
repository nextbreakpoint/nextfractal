/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

public class ShapeAdjustmentConfigElementNodeValue extends NodeValue<ShapeAdjustmentConfigElement> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ShapeAdjustmentConfigElementNodeValue(final ShapeAdjustmentConfigElement value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#getValueClone()
	 */
	@Override
	public ShapeAdjustmentConfigElement getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#clone()
	 */
	@Override
	public ShapeAdjustmentConfigElementNodeValue clone() {
		return new ShapeAdjustmentConfigElementNodeValue(getValueClone());
	}
}
