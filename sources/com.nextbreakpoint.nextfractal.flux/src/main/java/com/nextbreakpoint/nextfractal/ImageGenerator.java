package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;

public interface ImageGenerator {
	public IntBuffer renderImage(Object data);

	public int getHeight();

	public int getWidth();
}
