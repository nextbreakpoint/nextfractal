package com.nextbreakpoint.nextfractal.core.javaFX;

import javafx.beans.value.ObservableValueBase;

public class BooleanObservableValue extends ObservableValueBase<Boolean> {
	private Boolean value = Boolean.FALSE;
	
	public void setValue(Boolean value) {
		this.value = value;
		fireValueChangedEvent();
	}

	@Override
	public Boolean getValue() {
		return value;
	}
}
