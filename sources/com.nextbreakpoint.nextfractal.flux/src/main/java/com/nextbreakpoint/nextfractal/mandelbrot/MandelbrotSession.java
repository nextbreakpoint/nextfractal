package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nextbreakpoint.nextfractal.DataEncoder;
import com.nextbreakpoint.nextfractal.ExportSession;
import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.SessionException;
import com.nextbreakpoint.nextfractal.encoder.PNGImageEncoder;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public class MandelbrotSession implements FractalSession {
	private final List<FractalSessionListener> listeners = new ArrayList<>();
	private final List<ExportSession> sessions = new ArrayList<>();
	private MandelbrotData data = new MandelbrotData();
	private String packageName;
	private String className;
	private File outDir;

	/**
	 * @see com.nextbreakpoint.nextfractal.FractalSession#getPackageName()
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.FractalSession#setPackageName(java.lang.String)
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.FractalSession#setClassName(java.lang.String)
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.FractalSession#addSessionListener(com.nextbreakpoint.nextfractal.FractalSessionListener)
	 */
	@Override
	public void addSessionListener(FractalSessionListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.FractalSession#removeSessionListener(com.nextbreakpoint.nextfractal.FractalSessionListener)
	 */
	@Override
	public void removeSessionListener(FractalSessionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 */
	@Override
	public void terminate() {
		fireTerminate();
	}

	public File getOutDir() {
		return outDir;
	}

	public void setOutDir(File outDir) {
		this.outDir = outDir;
	}

	public String getSource() {
		return data.getSource();
	}
	
	public void setSource(String source) {
		data.setSource(source);
		fireDataChanged();
	}
	
	public double[] getConstant() {
		return data.getConstant();
	}

	public void setConstant(double[] constant) {
		data.setConstant(constant);
		fireDataChanged();
	}

	public boolean isJulia() {
		return data.isJulia();
	}

	public void setJulia(boolean julia) {
		data.setJulia(julia);
		fireDataChanged();
	}

	public double getTime() {
		return data.getTime();
	}

	public void setTime(double time) {
		data.setTime(time);
		fireDataChanged();
	}

	public void setView(MandelbrotView view, boolean zoom) {
		this.data.setTraslation(view.getTraslation());
		this.data.setRotation(view.getRotation());
		this.data.setScale(view.getScale());
		fireViewChanged(zoom);
	}

	public MandelbrotView getView() {
		return new MandelbrotView(data.getTraslation(), data.getRotation(), data.getScale());
	}

	public void setData(MandelbrotData data) {
		this.data.setConstant(data.getConstant());
		this.data.setSource(data.getSource());
		this.data.setJulia(data.isJulia());
		this.data.setTraslation(data.getTraslation());
		this.data.setRotation(data.getRotation());
		this.data.setScale(data.getScale());
		this.data.setTime(data.getTime());
		fireDataChanged();
	}

	public String getVersion() {
		return data.getVersion();
	}

	private void fireDataChanged() {
		for (FractalSessionListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

	private void fireViewChanged(boolean zoom) {
		for (FractalSessionListener listener : listeners) {
			listener.viewChanged(this, zoom);
		}
	}

	private void fireSessionAdded(ExportSession session) {
		for (FractalSessionListener listener : listeners) {
			listener.sessionAdded(this, session);
		}
	}
	
	private void fireSessionRemoved(ExportSession session) {
		for (FractalSessionListener listener : listeners) {
			listener.sessionRemoved(this, session);
		}
	}
	
	private void fireTerminate() {
		for (FractalSessionListener listener : listeners) {
			listener.terminate(this);
		}
	}

	public MandelbrotData toData() {
		MandelbrotData data = new MandelbrotData();
		data.setConstant(this.data.getConstant());
		data.setSource(this.data.getSource());
		data.setJulia(this.data.isJulia());
		data.setTraslation(this.data.getTraslation());
		data.setRotation(this.data.getRotation());
		data.setScale(this.data.getScale());
		data.setTime(this.data.getTime());
		return data;
	}

	public void add(ExportSession session) {
		sessions.add(session);
	}

	public void remove(ExportSession session) {
		sessions.remove(session);
	}

	public void getSessions() {
		Collections.unmodifiableCollection(sessions);
	}

	@Override
	public ExportSession createExportSession(File file, Object data, RendererSize size) throws SessionException {
		if (!(data instanceof MandelbrotData)) {
			throw new SessionException("Unsupported data");
		}
		try {
			ExportSession exportSession = new ExportSession(file, data, size, new DataEncoder(new PNGImageEncoder()));
			sessions.add(exportSession);
			fireSessionAdded(exportSession);
			return exportSession;
		} catch (Throwable e) {
			throw new SessionException("Failed to create session", e);
		}
	}
}
