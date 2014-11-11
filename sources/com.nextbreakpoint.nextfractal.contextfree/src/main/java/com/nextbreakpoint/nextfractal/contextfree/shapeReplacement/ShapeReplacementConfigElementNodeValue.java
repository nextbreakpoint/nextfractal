/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeReplacement;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

public class ShapeReplacementConfigElementNodeValue extends NodeValue<ShapeReplacementConfigElement> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ShapeReplacementConfigElementNodeValue(final ShapeReplacementConfigElement value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#getValueClone()
	 */
	@Override
	public ShapeReplacementConfigElement getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#clone()
	 */
	@Override
	public ShapeReplacementConfigElementNodeValue clone() {
		return new ShapeReplacementConfigElementNodeValue(getValueClone());
	}
}
