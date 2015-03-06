package com.nextbreakpoint.nextfractal.core.renderer;

public interface RendererFactory {
	public RendererBuffer createBuffer(int widh, int height);
	
	public RendererGraphicsContext createGraphicsContext(Object context);

	public RendererAffine createTranslateAffine(double x, double y);

	public RendererAffine createRotateAffine(double a, double centerX, double centerY);

	public RendererColor createColor(double red, double green, double blue, double opacity);

	public RendererAffine createAffine();
}
