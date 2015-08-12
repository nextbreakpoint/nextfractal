/*
 * NextFractal 1.1.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
