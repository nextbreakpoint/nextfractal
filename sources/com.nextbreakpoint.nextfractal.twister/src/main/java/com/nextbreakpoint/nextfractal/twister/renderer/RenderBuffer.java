package com.nextbreakpoint.nextfractal.twister.renderer;

public interface RenderBuffer {
	public int getWidth();
	
	public int getHeight();
	
	public void dispose();

	public void clear();

	public void update(int[] pixels);

	public RenderImage getImage();
}
