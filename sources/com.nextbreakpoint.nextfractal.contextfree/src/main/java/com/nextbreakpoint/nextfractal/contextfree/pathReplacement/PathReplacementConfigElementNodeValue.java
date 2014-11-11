/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathReplacement;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

public class PathReplacementConfigElementNodeValue extends NodeValue<PathReplacementConfigElement> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public PathReplacementConfigElementNodeValue(final PathReplacementConfigElement value) {
		super(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#getValueClone()
	 */
	@Override
	public PathReplacementConfigElement getValueClone() {
		if (getValue() != null) {
			return getValue().clone();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue#clone()
	 */
	@Override
	public PathReplacementConfigElementNodeValue clone() {
		return new PathReplacementConfigElementNodeValue(getValueClone());
	}
}
