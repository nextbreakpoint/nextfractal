package com.nextbreakpoint.nextfractal.core.session;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;


public interface SessionListener {
	/**
	 * @param session
	 */
	public void dataChanged(Session session);
	
	/**
	 * @param session
	 * @param continuous 
	 */
	public void pointChanged(Session session, boolean continuous);

	/**
	 * @param session
	 * @param continuous 
	 */
	public void viewChanged(Session session, boolean continuous);

	/**
	 * @param session
	 */
	public void terminate(Session session);
	
	/**
	 * @param session
	 */
	public void fractalChanged(Session session);

	/**
	 * @param session
	 * @param exportSession
	 */
	public void sessionAdded(Session session, ExportSession exportSession);

	/**
	 * @param session
	 * @param exportSession
	 */
	public void sessionRemoved(Session session, ExportSession exportSession);
}
