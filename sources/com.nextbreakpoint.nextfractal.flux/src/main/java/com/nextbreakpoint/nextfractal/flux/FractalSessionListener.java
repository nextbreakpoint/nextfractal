package com.nextbreakpoint.nextfractal.flux;

public interface FractalSessionListener {
	/**
	 * @param session
	 */
	public void sourceChanged(FractalSession session);

	/**
	 * @param session
	 */
	public void fractalChanged(FractalSession session);
}
