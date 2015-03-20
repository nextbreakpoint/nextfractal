package com.nextbreakpoint.nextfractal.core.session;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;

public abstract class Session {
	protected final List<SessionListener> listeners = new ArrayList<>();
	protected final List<ExportSession> sessions = new ArrayList<>();
	protected ExportService exportService;

	public void addSessionListener(SessionListener listener) {
		listeners.add(listener);
	}

	public void removeSessionListener(SessionListener listener) {
		listeners.remove(listener);
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

	protected void fireDataChanged() {
		for (SessionListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

	protected void firePointChanged(boolean continuous) {
		for (SessionListener listener : listeners) {
			listener.pointChanged(this, continuous);
		}
	}

	protected void fireViewChanged(boolean continuous) {
		for (SessionListener listener : listeners) {
			listener.viewChanged(this, continuous);
		}
	}

	protected void fireFractalChanged() {
		for (SessionListener listener : listeners) {
			listener.fractalChanged(this);
		}
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

	public ExportService getExportService() {
		return exportService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
}
