package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.core.View;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public class MandelbrotSession implements FractalSession {
	private final List<FractalSessionListener> listeners = new ArrayList<>();
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
	
	public Number getConstant() {
		return data.getConstant();
	}

	public void setConstant(Number constant) {
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

	public void setView(View view, boolean zoom) {
		this.data.setView(view);
		fireViewChanged(zoom);
	}

	public View getView() {
		return data.getView();
	}

	public void setData(MandelbrotData data) {
		this.data.setConstant(data.getConstant());
		this.data.setSource(data.getSource());
		this.data.setJulia(data.isJulia());
		this.data.setView(data.getView());
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
		data.setView(this.data.getView());
		data.setTime(this.data.getTime());
		return data;
	}
}
