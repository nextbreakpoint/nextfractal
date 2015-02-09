package com.nextbreakpoint.nextfractal.spool.store;

public abstract class StoreData {
	private Object data;

	public StoreData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
