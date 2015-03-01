package com.nextbreakpoint.nextfractal.render;

public interface RenderFactory {
	public RenderBuffer createBuffer(int widh, int height);
	
	public RenderGraphicsContext createGraphicsContext(Object context);

	public RenderAffine createTranslateAffine(double x, double y);

	public RenderAffine createRotateAffine(double a, double centerX, double centerY);

	public RenderColor createColor(double red, double green, double blue, double opacity);

	public RenderAffine createAffine();
}
