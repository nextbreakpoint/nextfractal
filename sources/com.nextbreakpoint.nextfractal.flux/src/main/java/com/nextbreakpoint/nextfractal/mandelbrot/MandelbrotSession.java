package com.nextbreakpoint.nextfractal.mandelbrot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;

public class MandelbrotSession implements FractalSession {
	private final List<FractalSessionListener> listeners = new ArrayList<>();
	private MandelbrotData data = new MandelbrotData();
	private String packageName;
	private String className;
	private File outDir;
	
	/**
	 * @see com.nextbreakpoint.nextfractal.FractalSession#getSource()
	 */
	public String getSource() {
		return data.getSource();
	}

	/**
	 * @param source
	 */
	public void setSource(String source) {
		data.setSource(source);
		for (FractalSessionListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

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
		for (FractalSessionListener listener : listeners) {
			listener.terminate(this);
		}
	}

	public File getOutDir() {
		return outDir;
	}

	public void setOutDir(File outDir) {
		this.outDir = outDir;
	}

	@Override
	public void setData(Object data) {
		this.data = (MandelbrotData)data;
	}

	@Override
	public Object getData() {
		return data;
	}
}
