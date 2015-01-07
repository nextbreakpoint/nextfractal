package com.nextbreakpoint.nextfractal.flux;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public interface FractalSession {
	/**
	 * @param listener
	 */
	public void addSessionListener(FractalSessionListener listener);

	/**
	 * @param listener
	 */
	public void removeSessionListener(FractalSessionListener listener);

	/**
	 * @param source
	 */
	public void setSource(String source);

	/**
	 * @return
	 */
	public String getSource();

	/**
	 * @param fractal
	 */
	public void setFractal(RendererFractal fractal);

	/**
	 * @return
	 */
	public RendererFractal getFractal();
}
