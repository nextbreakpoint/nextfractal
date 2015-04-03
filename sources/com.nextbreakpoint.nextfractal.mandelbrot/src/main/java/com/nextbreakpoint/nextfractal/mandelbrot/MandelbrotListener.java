package com.nextbreakpoint.nextfractal.mandelbrot;


public interface MandelbrotListener {
	/**
	 * @param session
	 */
	public void dataChanged(MandelbrotSession session);

	/**
	 * @param session
	 */
	public void sourceChanged(MandelbrotSession session);
	
	/**
	 * @param session
	 */
	public void reportChanged(MandelbrotSession session);
	
	/**
	 * @param session
	 * @param continuous 
	 */
	public void pointChanged(MandelbrotSession session, boolean continuous);

	/**
	 * @param session
	 * @param continuous 
	 */
	public void viewChanged(MandelbrotSession session, boolean continuous);
}
