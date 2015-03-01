package com.nextbreakpoint.nextfractal;

import java.util.ArrayList;
import java.util.List;

public abstract class FractalSession {
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

	protected void firePointChanged() {
		for (SessionListener listener : listeners) {
			listener.pointChanged(this);
		}
	}

	protected void fireViewChanged(boolean zoom) {
		for (SessionListener listener : listeners) {
			listener.viewChanged(this, zoom);
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
