/*
 * NextFractal 1.0.5
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
package com.nextbreakpoint.nextfractal.core.session;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;

public abstract class AbstractSession implements Session {
	private final List<SessionListener> listeners = new ArrayList<>();
	private final List<ExportSession> sessions = new ArrayList<>();
	private ExportService exportService;

	public void addSessionListener(SessionListener listener) {
		listeners.add(listener);
	}

	public void removeSessionListener(SessionListener listener) {
		listeners.remove(listener);
	}

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	public void terminate() {
		fireTerminate();
	}

	public void addExportSession(ExportSession exportSession) {
		sessions.add(exportSession);
		fireSessionAdded(exportSession);
	}

	public void removeExportSession(ExportSession exportSession) {
		sessions.remove(exportSession);
		fireSessionRemoved(exportSession);
	}
	
	protected void fireTerminate() {
		for (SessionListener listener : listeners) {
			listener.terminate(this);
		}
	}

	protected void fireSessionAdded(ExportSession session) {
		for (SessionListener listener : listeners) {
			listener.sessionAdded(this, session);
		}
	}
	
	protected void fireSessionRemoved(ExportSession session) {
		for (SessionListener listener : listeners) {
			listener.sessionRemoved(this, session);
		}
	}

	public void doExportAsImage() {
		for (SessionListener listener : listeners) {
			listener.doExportAsImage(this);
		}
	}
}
