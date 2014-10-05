package com.nextbreakpoint.nextfractal.twister.renderer;

public interface RenderAffine {
	public void setAffine(RenderGraphicsContext context);

	public void append(RenderAffine affine);
}
