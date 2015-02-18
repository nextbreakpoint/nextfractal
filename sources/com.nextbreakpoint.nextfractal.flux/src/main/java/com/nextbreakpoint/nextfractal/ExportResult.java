package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;

public class ExportResult {
	private final IntBuffer pixels;
	private final String error;
	
	public ExportResult(IntBuffer pixels, String error) {
		this.pixels = pixels;
		this.error = error;
	}

	public IntBuffer getPixels() {
		return pixels;
	}

	public String getError() {
		return error;
	}
}
