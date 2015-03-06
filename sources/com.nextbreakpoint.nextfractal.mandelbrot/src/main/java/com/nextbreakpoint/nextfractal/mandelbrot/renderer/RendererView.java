package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

/**
 * @author Andrea Medeghini
 */
public class RendererView {
	private Double4D traslation;
	private Double4D rotation;
	private Double4D scale;
	private Integer4D state;
	private Number point;
	private boolean julia;
	
	public RendererView() {
		traslation = new Double4D(0, 0, 1, 0);
		rotation = new Double4D(0, 0, 0, 0);
		scale = new Double4D(1, 1, 1, 1);
		state = new Integer4D(0, 0, 0, 0);
		point = new Number(0, 0);
	}

	public RendererView(MandelbrotView view) {
		traslation = new Double4D(view.getTraslation());
		rotation = new Double4D(view.getRotation());
		scale = new Double4D(view.getScale());
		state = new Integer4D(0, 0, 0, 0);
		point = new Number(0, 0);
	}
	
	public Double4D getTraslation() {
		return traslation;
	}

	public void setTraslation(Double4D translation) {
		this.traslation = translation;
	}

	public Double4D getRotation() {
		return rotation;
	}

	public void setRotation(Double4D rotation) {
		this.rotation = rotation;
	}

	public Double4D getScale() {
		return scale;
	}

	public void setScale(Double4D scale) {
		this.scale = scale;
	}

	public Number getPoint() {
		return point;
	}

	public void setPoint(Number point) {
		this.point = point;
	}

	public boolean isJulia() {
		return julia;
	}

	public void setJulia(boolean julia) {
		this.julia = julia;
	}

	public Integer4D getState() {
		return state;
	}

	public void setState(Integer4D state) {
		this.state = state;
	}
}