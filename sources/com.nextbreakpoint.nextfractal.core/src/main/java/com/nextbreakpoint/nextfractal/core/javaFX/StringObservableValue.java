package com.nextbreakpoint.nextfractal.core.javaFX;

import javafx.beans.value.ObservableValueBase;

public class StringObservableValue extends ObservableValueBase<String> {
	private String value;

	public void setValue(String value) {
		this.value = value;
		fireValueChangedEvent();
	}

	@Override
	public String getValue() {
		return value;
	}
}
