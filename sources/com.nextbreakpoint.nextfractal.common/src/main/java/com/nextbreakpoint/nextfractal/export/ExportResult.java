package com.nextbreakpoint.nextfractal.export;

public class ExportResult {
	private final String error;
	
	public ExportResult(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
