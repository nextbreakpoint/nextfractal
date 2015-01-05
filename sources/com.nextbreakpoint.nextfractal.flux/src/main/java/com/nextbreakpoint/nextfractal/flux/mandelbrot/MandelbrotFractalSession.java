package com.nextbreakpoint.nextfractal.flux.mandelbrot;

import com.nextbreakpoint.nextfractal.flux.core.FractalSession;
import com.nextbreakpoint.nextfractal.flux.core.FractalSessionDelegate;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public class MandelbrotFractalSession implements FractalSession {
	private FractalSessionDelegate delegate;
	private RendererFractal rendererFractal;
	
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.core.FractalSession#setDelegate(com.nextbreakpoint.nextfractal.flux.core.FractalSessionDelegate)
	 */
	@Override
	public void setDelegate(FractalSessionDelegate delegate) {
		this.delegate = delegate;
	}

	/**
	 * @return
	 */
	public RendererFractal getRendererFractal() {
		return rendererFractal;
	}

	/**
	 * @param rendererFractal
	 */
	public void setRendererFractal(RendererFractal rendererFractal) {
		this.rendererFractal = rendererFractal;
		if (delegate != null) {
			delegate.fractalChanged();
		}
	}
}
