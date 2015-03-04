package com.nextbreakpoint.nextfractal.mandelbrot.renderer.strategy;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererFractal;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererState;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy;
import com.nextbreakpoint.nextfractal.utils.Colors;

public class JuliaRendererStrategy implements RendererStrategy {
	private RendererFractal rendererFractal;

	public JuliaRendererStrategy(RendererFractal rendererFractal) {
		this.rendererFractal = rendererFractal;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#prepare()
	 */
	@Override
	public void prepare() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererState.renderer.RenderedPoint)
	 */
	@Override
	public int renderPoint(RendererState p, Number x, Number w) {
		rendererFractal.renderOrbit(p.vars(), w, x);
		return renderColor(p);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy#renderColor(com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererState)
	 */
	@Override
	public int renderColor(RendererState p) {
		return Colors.color(rendererFractal.renderColor(p.vars()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy#isSolidGuessSupported()
	 */
	@Override
	public boolean isSolidGuessSupported() {
		return rendererFractal.isSolidGuessSupported();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy#isVerticalSymetrySupported()
	 */
	@Override
	public boolean isVerticalSymetrySupported() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy#isHorizontalSymetrySupported()
	 */
	@Override
	public boolean isHorizontalSymetrySupported() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy#getVerticalSymetryPoint()
	 */
	@Override
	public double getVerticalSymetryPoint() {
		return rendererFractal.getVerticalSymetryPoint();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererStrategy#getHorizontalSymetryPoint()
	 */
	@Override
	public double getHorizontalSymetryPoint() {
		return rendererFractal.getHorizontalSymetryPoint();
	}
}