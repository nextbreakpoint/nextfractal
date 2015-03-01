package com.nextbreakpoint.nextfractal;


public interface SessionListener {
	/**
	 * @param session
	 */
	public void dataChanged(FractalSession session);
	
	/**
	 * @param session
	 * @param continuous 
	 */
	public void pointChanged(FractalSession session, boolean continuous);

	/**
	 * @param session
	 * @param continuous 
	 */
	public void viewChanged(FractalSession session, boolean continuous);

	/**
	 * @param session
	 */
	public void terminate(FractalSession session);

	/**
	 * @param session
	 * @param exportSession
	 */
	public void sessionAdded(FractalSession session, ExportSession exportSession);

	/**
	 * @param session
	 * @param exportSession
	 */
	public void sessionRemoved(FractalSession session, ExportSession exportSession);
}
