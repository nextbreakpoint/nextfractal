package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class FractalSession {
	protected final List<SessionListener> listeners = new ArrayList<>();
	protected final List<ExportSession> sessions = new ArrayList<>();
	private String packageName;
	private String className;
	private File outDir;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void addSessionListener(SessionListener listener) {
		listeners.add(listener);
	}

	public void removeSessionListener(SessionListener listener) {
		listeners.remove(listener);
	}

	public void terminate() {
		fireTerminate();
	}

	public File getOutDir() {
		return outDir;
	}

	public void setOutDir(File outDir) {
		this.outDir = outDir;
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
}
