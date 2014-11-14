package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

@FunctionalInterface
public interface SelectionDelegate<T> {
	public void didSelectValue(T node);
}

