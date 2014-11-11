/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.figure;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

public class FigureConfigElementNodeValue extends NodeValue<FigureConfigElement> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public FigureConfigElementNodeValue(final FigureConfigElement value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#getValueClone()
	 */
	@Override
	public FigureConfigElement getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#clone()
	 */
	@Override
	public FigureConfigElementNodeValue clone() {
		return new FigureConfigElementNodeValue(getValueClone());
	}
}
