package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public interface ImageGenerator {
	public IntBuffer renderImage(Object data);

	public RendererSize getSize();

	public void setStopCondition(Condition condition);
}
