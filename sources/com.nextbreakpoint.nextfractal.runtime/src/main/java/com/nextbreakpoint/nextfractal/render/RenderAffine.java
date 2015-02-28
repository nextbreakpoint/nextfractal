package com.nextbreakpoint.nextfractal.render;

public interface RenderAffine {
	public void setAffine(RenderGraphicsContext context);

	public void append(RenderAffine affine);
}
