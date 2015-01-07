package com.nextbreakpoint.nextfractal.flux.mandelbrot;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.FractalSessionListener;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public class MandelbrotFractalSession implements FractalSession {
	private final List<FractalSessionListener> listeners = new ArrayList<>();
	private RendererFractal fractal;
	private String source;
	
	/**
	 * @return
	 */
	@Override
	public RendererFractal getFractal() {
		return fractal;
	}

	/**
	 * @param fractal
	 */
	@Override
	public void setFractal(RendererFractal fractal) {
		this.fractal = fractal;
		for (FractalSessionListener listener : listeners) {
			listener.fractalChanged(this);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#getSource()
	 */
	@Override
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	@Override
	public void setSource(String source) {
		this.source = source;
		for (FractalSessionListener listener : listeners) {
			listener.sourceChanged(this);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#addSessionListener(com.nextbreakpoint.nextfractal.flux.FractalSessionListener)
	 */
	@Override
	public void addSessionListener(FractalSessionListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#removeSessionListener(com.nextbreakpoint.nextfractal.flux.FractalSessionListener)
	 */
	@Override
	public void removeSessionListener(FractalSessionListener listener) {
		listeners.remove(listener);
	}
}
