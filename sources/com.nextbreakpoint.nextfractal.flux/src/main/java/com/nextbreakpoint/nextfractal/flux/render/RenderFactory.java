package com.nextbreakpoint.nextfractal.flux.render;

public interface RenderFactory {
	public RenderBuffer createBuffer(int widh, int height);
	
	public RenderGraphicsContext createGraphicsContext(Object context);

	public RenderAffine createTranslateAffine(double x, double y);

	public RenderAffine createRotateAffine(double a, double centerX, double centerY);

	public RenderColor createColor(int red, int green, int blue, int opacity);

	public RenderAffine createAffine();
}
