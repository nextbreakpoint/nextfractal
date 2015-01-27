package com.nextbreakpoint.nextfractal.mandelbrot;

import javax.xml.bind.annotation.XmlRootElement;

import com.nextbreakpoint.nextfractal.core.DoubleVector4D;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

@XmlRootElement(name="mandelbrot")
public class MandelbrotData {
	private DoubleVector4D traslation = new DoubleVector4D(0, 0, 0, 0);
	private DoubleVector4D rotation = new DoubleVector4D(0, 0, 0, 0);
	private DoubleVector4D scale = new DoubleVector4D(1, 1, 1, 1);
	private Number constant = new Number(0, 0);
	private boolean julia;
	private String source = "";
	private final String version = "1.0";
	private double time;

	public DoubleVector4D getTraslation() {
		return traslation;
	}

	public void setTraslation(DoubleVector4D traslation) {
		this.traslation = traslation;
	}

	public DoubleVector4D getRotation() {
		return rotation;
	}

	public void setRotation(DoubleVector4D rotation) {
		this.rotation = rotation;
	}

	public DoubleVector4D getScale() {
		return scale;
	}

	public void setScale(DoubleVector4D scale) {
		this.scale = scale;
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
		return "[traslation=" + traslation + ", rotation=" + rotation + ", scale=" + scale + ", constant=" + constant + ", julia=" + julia + ", time=" + time + ", version=" + version + "]";
	}
}
