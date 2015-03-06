package com.nextbreakpoint.nextfractal.core.renderer;

public interface RendererAffine {
	public void setAffine(RendererGraphicsContext context);

	public void append(RendererAffine affine);
}
