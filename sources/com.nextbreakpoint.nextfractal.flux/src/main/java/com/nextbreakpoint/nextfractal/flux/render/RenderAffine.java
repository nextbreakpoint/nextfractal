package com.nextbreakpoint.nextfractal.flux.render;

public interface RenderAffine {
	public void setAffine(RenderGraphicsContext context);

	public void append(RenderAffine affine);
}
