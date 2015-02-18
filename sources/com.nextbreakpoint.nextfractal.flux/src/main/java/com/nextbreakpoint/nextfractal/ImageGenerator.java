package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.nio.IntBuffer;

public interface ImageGenerator {
	public IntBuffer renderImage(File outDir, Object data);

	public int getHeight();

	public int getWidth();
}
