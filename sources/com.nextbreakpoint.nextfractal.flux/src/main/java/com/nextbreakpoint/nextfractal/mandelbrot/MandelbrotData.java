package com.nextbreakpoint.nextfractal.mandelbrot;

import javax.xml.bind.annotation.XmlRootElement;

import com.nextbreakpoint.nextfractal.core.View;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

@XmlRootElement(name="mandelbrot")
public class MandelbrotData {
	private final static String version = "1.0";
	private Number constant = new Number(0, 0);
	private View view = new View();
	private String source = "";
	private boolean julia;
	private double time;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Number getConstant() {
		return constant;
	}

	public void setConstant(Number constant) {
		this.constant = constant;
	}

	public boolean isJulia() {
		return julia;
	}

	public void setJulia(boolean julia) {
		this.julia = julia;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "[view=" + view + ", constant=" + constant + ", julia=" + julia + ", time=" + time + ", version=" + version + "]";
	}
}
