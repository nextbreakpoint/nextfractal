package com.nextbreakpoint.nextfractal;

public interface FractalSessionListener {
	/**
	 * @param session
	 */
	public void sourceChanged(FractalSession session);

	/**
	 * @param session
	 */
	public void terminate(FractalSession session);
}
