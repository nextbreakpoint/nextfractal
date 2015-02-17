package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.ExportService;
import com.nextbreakpoint.nextfractal.FractalSession;

public class MandelbrotSession extends FractalSession {
	private MandelbrotData data = new MandelbrotData();

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
	
	public MandelbrotData getData() {
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
}
