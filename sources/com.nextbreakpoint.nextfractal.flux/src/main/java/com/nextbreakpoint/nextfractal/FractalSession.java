package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nextbreakpoint.nextfractal.encoder.PNGImageEncoder;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public abstract class FractalSession {
	protected final List<FractalSessionListener> listeners = new ArrayList<>();
	protected final List<ExportSession> sessions = new ArrayList<>();
	private ExportService exportService;
	private String packageName;
	private String className;
	private File outDir;

	public FractalSession(ExportService exportService) {
		this.exportService = exportService;
	}
	
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

	public void addSessionListener(FractalSessionListener listener) {
		listeners.add(listener);
	}

	public void removeSessionListener(FractalSessionListener listener) {
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

	protected void fireSessionAdded(ExportSession session) {
		for (FractalSessionListener listener : listeners) {
			listener.sessionAdded(this, session);
		}
	}
	
	protected void fireSessionRemoved(ExportSession session) {
		for (FractalSessionListener listener : listeners) {
			listener.sessionRemoved(this, session);
		}
	}

	protected void fireDataChanged() {
		for (FractalSessionListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

	protected void fireViewChanged(boolean zoom) {
		for (FractalSessionListener listener : listeners) {
			listener.viewChanged(this, zoom);
		}
	}
	
	protected void fireTerminate() {
		for (FractalSessionListener listener : listeners) {
			listener.terminate(this);
		}
	}

	public void addExportSession(ExportSession exportSession) {
		sessions.add(exportSession);
		fireSessionAdded(exportSession);
	}

	public void removeExportSession(ExportSession exportSession) {
		sessions.remove(exportSession);
		fireSessionAdded(exportSession);
	}

	public void getSessions() {
		Collections.unmodifiableCollection(sessions);
	}

	public ExportSession createExportSession(File file, Object data, RendererSize size) throws SessionException {
		if (!(data instanceof MandelbrotData)) {
			throw new SessionException("Unsupported data");
		}
		try {
			return new ExportSession(exportService, file, data, size, new DataEncoder(new PNGImageEncoder()));
		} catch (Throwable e) {
			throw new SessionException("Failed to create session", e);
		}
	}
}
