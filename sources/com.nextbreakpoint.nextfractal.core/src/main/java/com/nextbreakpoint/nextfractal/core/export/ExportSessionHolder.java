package com.nextbreakpoint.nextfractal.core.export;

import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.session.SessionState;

public class ExportSessionHolder {
	private static final Logger logger = Logger.getLogger(ExportSessionHolder.class.getName());
	private ExportSession session;

	protected ExportSessionHolder(ExportSession session) {
		this.session = session;
	}

	public ExportSession getSession() {
		return session;
	}

	public void setState(SessionState state) {
		session.setState(state);
		logger.fine(session.getSessionId() + " -> state = " + state);
	}
}
