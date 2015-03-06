package com.nextbreakpoint.nextfractal.core.renderer;

import java.nio.IntBuffer;

public interface RendererImage {
	public void draw(RendererGraphicsContext context, int x, int y);
	
	public void draw(RendererGraphicsContext context, int x, int y, int w, int h);

	public void getPixels(IntBuffer pixels);
}
