package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.utils.Condition;

public interface ImageGenerator {
	public IntBuffer renderImage(Object data);

	public RendererSize getSize();

	public void setStopCondition(Condition condition);

	public boolean isInterrupted();
}
