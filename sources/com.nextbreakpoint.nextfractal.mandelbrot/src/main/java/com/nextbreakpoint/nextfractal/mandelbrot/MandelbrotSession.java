package com.nextbreakpoint.nextfractal.mandelbrot;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.session.AbstractSession;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;

public class MandelbrotSession extends AbstractSession {
	private final List<MandelbrotListener> listeners = new ArrayList<>();
	private MandelbrotData data = new MandelbrotData();
	private CompilerReport report;
	
	public void addMandelbrotListener(MandelbrotListener listener) {
		listeners.add(listener);
	}

	public void removeMandelbrotListener(MandelbrotListener listener) {
		listeners.remove(listener);
	}

	public String getVersion() {
		return data.getVersion();
	}

	public String getSource() {
		return data.getSource();
	}
	
	public void setSource(String source) {
		if (!data.getSource().equals(source)) {
			data.setSource(source);
			fireSourceChanged();
		}
	}
	
	public CompilerReport getReport() {
		return report;
	}

	public void setReport(CompilerReport report) {
		this.report = report;
		fireReportChanged();
	}

	public double getTime() {
		return data.getTime();
	}

	public void setTime(double time) {
		data.setTime(time);
		fireDataChanged();
	}

	public double[] getPoint() {
		return data.getPoint();
	}
	
	public void setPoint(double[] point, boolean continuous) {
		data.setPoint(point);
		firePointChanged(continuous);
	}

	public void setView(MandelbrotView view, boolean continuous) {
		this.data.setTraslation(view.getTraslation());
		this.data.setRotation(view.getRotation());
		this.data.setScale(view.getScale());
		this.data.setPoint(view.getPoint());
		this.data.setJulia(view.isJulia());
		fireViewChanged(continuous);
	}

	public MandelbrotView getView() {
		return new MandelbrotView(data.getTraslation(), data.getRotation(), data.getScale(), data.getPoint(), data.isJulia());
	}

	public void setData(MandelbrotData data) {
		this.data.setSource(data.getSource());
		this.data.setTraslation(data.getTraslation());
		this.data.setRotation(data.getRotation());
		this.data.setScale(data.getScale());
		this.data.setTime(data.getTime());
		this.data.setPoint(data.getPoint());
		this.data.setJulia(data.isJulia());
		fireDataChanged();
	}

	public MandelbrotData getData() {
		MandelbrotData data = new MandelbrotData();
		data.setSource(this.data.getSource());
		data.setTraslation(this.data.getTraslation());
		data.setRotation(this.data.getRotation());
		data.setScale(this.data.getScale());
		data.setTime(this.data.getTime());
		data.setPoint(this.data.getPoint());
		data.setJulia(this.data.isJulia());
		return data;
	}

	protected void fireDataChanged() {
		for (MandelbrotListener listener : listeners) {
			listener.dataChanged(this);
		}
	}

	protected void fireSourceChanged() {
		for (MandelbrotListener listener : listeners) {
			listener.sourceChanged(this);
		}
	}

	protected void fireReportChanged() {
		for (MandelbrotListener listener : listeners) {
			listener.reportChanged(this);
		}
	}

	protected void firePointChanged(boolean continuous) {
		for (MandelbrotListener listener : listeners) {
			listener.pointChanged(this, continuous);
		}
	}

	protected void fireViewChanged(boolean continuous) {
		for (MandelbrotListener listener : listeners) {
			listener.viewChanged(this, continuous);
		}
	}
}