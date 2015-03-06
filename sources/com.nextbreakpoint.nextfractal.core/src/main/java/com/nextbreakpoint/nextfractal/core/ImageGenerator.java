package com.nextbreakpoint.nextfractal.core;

import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;

public interface ImageGenerator {
	public IntBuffer renderImage(Object data);

	public RendererSize getSize();

	public boolean isInterrupted();
}
