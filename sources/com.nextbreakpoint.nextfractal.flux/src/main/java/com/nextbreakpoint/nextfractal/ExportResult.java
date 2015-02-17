package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;

public class ExportResult {
	private IntBuffer pixels;
	private String errorMessage;

	public IntBuffer getPixels() {
		return pixels;
	}

	public void setPixels(IntBuffer pixels) {
		this.pixels = pixels;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
