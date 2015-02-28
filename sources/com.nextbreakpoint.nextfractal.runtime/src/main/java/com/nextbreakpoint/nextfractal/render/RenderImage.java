package com.nextbreakpoint.nextfractal.render;

import java.nio.IntBuffer;

public interface RenderImage {
	public void draw(RenderGraphicsContext context, int x, int y);
	
	public void draw(RenderGraphicsContext context, int x, int y, int w, int h);

	public void getPixels(IntBuffer pixels);
}
