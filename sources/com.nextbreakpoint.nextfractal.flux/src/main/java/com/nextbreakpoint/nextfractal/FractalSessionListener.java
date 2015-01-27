package com.nextbreakpoint.nextfractal;

public interface FractalSessionListener {
	/**
	 * @param session
	 */
	public void dataChanged(FractalSession session);

	/**
	 * @param session
	 */
	public void terminate(FractalSession session);
}
