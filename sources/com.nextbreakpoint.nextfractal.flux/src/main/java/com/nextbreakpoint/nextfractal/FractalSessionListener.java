package com.nextbreakpoint.nextfractal;

public interface FractalSessionListener {
	/**
	 * @param session
	 */
	public void dataChanged(FractalSession session);

	/**
	 * @param session
	 * @param zoom 
	 */
	public void viewChanged(FractalSession session, boolean zoom);

	/**
	 * @param session
	 */
	public void terminate(FractalSession session);
}
