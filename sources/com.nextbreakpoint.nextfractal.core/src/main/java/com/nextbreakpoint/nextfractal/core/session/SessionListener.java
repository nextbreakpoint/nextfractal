package com.nextbreakpoint.nextfractal.core.session;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;

public interface SessionListener {
	/**
	 * @param session
	 */
	public void terminate(Session session);
	
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
