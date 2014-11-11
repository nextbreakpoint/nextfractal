package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

@FunctionalInterface
public interface ElementGridDelegate {
	public void didSelectValue(NodeValue<?> node);
}

