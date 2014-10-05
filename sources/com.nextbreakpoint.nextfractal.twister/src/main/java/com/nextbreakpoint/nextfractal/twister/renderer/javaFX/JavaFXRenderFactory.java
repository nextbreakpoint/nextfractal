package com.nextbreakpoint.nextfractal.twister.renderer.javaFX;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.twister.renderer.RenderAffine;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderColor;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;

public class JavaFXRenderFactory implements RenderFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory#createBuffer(int, int)
	 */
	@Override
	public RenderBuffer createBuffer(int widh, int height) {
		return new JavaFXRenderBuffer(widh, height);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory#createGraphicsContext(java.lang.Object)
	 */
	@Override
	public RenderGraphicsContext createGraphicsContext(Object context) {
		return new JavaFXRenderGraphicsContext((GraphicsContext)context);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory#createTranslateAffine(double, double)
	 */
	@Override
	public RenderAffine createTranslateAffine(double x, double y) {
		return new JavaFXRenderAffine(new Affine(Affine.translate(x, y)));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory#createRotateAffine(double, double, double)
	 */
	@Override
	public RenderAffine createRotateAffine(double a, double centerX, double centerY) {
		return new JavaFXRenderAffine(new Affine(Affine.rotate(a, centerX, centerY)));
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory#createAffine()
	 */
	@Override
	public RenderAffine createAffine() {
		return new JavaFXRenderAffine(new Affine());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory#createColor(int, int, int, int)
	 */
	@Override
	public RenderColor createColor(int red, int green, int blue, int opacity) {
		return new JavaFXRenderColor(red, green, blue, opacity);
	}
}
