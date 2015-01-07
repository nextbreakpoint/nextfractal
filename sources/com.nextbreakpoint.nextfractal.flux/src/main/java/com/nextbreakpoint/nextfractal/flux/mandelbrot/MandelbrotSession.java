package com.nextbreakpoint.nextfractal.flux.mandelbrot;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.FractalSessionListener;

public class MandelbrotSession implements FractalSession {
	private final List<FractalSessionListener> listeners = new ArrayList<>();
	private String packageName;
	private String className;
	private String source;
	
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#getSource()
	 */
	@Override
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	@Override
	public void setSource(String source) {
		this.source = source;
		for (FractalSessionListener listener : listeners) {
			listener.sourceChanged(this);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#getPackageName()
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#setPackageName(java.lang.String)
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
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#setClassName(java.lang.String)
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#addSessionListener(com.nextbreakpoint.nextfractal.flux.FractalSessionListener)
	 */
	@Override
	public void addSessionListener(FractalSessionListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.FractalSession#removeSessionListener(com.nextbreakpoint.nextfractal.flux.FractalSessionListener)
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
}
