package com.nextbreakpoint.nextfractal.core.renderer;

public interface RendererBuffer {
	public int getWidth();
	
	public int getHeight();
	
	public void dispose();

	public void clear();

	public void update(int[] pixels);

	public RendererImage getImage();
}
