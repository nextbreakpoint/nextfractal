/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.common;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class StrokeWidthElement extends ValueConfigElement<Float> {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "StrokeWidth";
	private Float minimum;
	private Float maximum;
	private Float step;

	/**
	 * @param defaultValue
	 */
	public StrokeWidthElement(final Float defaultValue) {
		super(StrokeWidthElement.CLASS_ID, defaultValue);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElement#clone()
	 */
	@Override
	public StrokeWidthElement clone() {
		StrokeWidthElement StrokeWidthElement = new StrokeWidthElement(getValue());
		StrokeWidthElement.setMaximum(getMaximum());
		StrokeWidthElement.setMinimum(getMinimum());
		StrokeWidthElement.setStep(getStep());
		return StrokeWidthElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		final StrokeWidthElement element = (StrokeWidthElement) source;
		setMaximum(element.getMaximum());
		setMinimum(element.getMinimum());
		setValue(element.getValue());
	}

	/**
	 * @return the minimum
	 */
	public Float getMinimum() {
		return minimum;
	}

	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(final Float minimum) {
		this.minimum = minimum;
	}

	/**
	 * @return the maximum
	 */
	public Float getMaximum() {
		return maximum;
	}

	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(final Float maximum) {
		this.maximum = maximum;
	}

	/**
	 * @return the step
	 */
	public Float getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(final Float step) {
		this.step = step;
	}
}
