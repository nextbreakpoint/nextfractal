package com.nextbreakpoint.nextfractal.core;

import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.utils.Condition;

public interface ImageGenerator {
	public IntBuffer renderImage(Object data);

	public RendererSize getSize();

	public void setStopCondition(Condition condition);

	public boolean isInterrupted();
}
