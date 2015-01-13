package com.nextbreakpoint.nextfractal.render;

public interface RenderImage {
	public void draw(RenderGraphicsContext context, int x, int y);
	
	public void draw(RenderGraphicsContext context, int x, int y, int w, int h);
}
