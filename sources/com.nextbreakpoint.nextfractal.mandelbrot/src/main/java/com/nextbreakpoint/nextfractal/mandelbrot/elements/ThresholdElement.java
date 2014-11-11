/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class ThresholdElement extends ValueConfigElement<Double> {
	public static final String CLASS_ID = "Threshold";
	private static final long serialVersionUID = 1L;
	private Double minimum;
	private Double maximum;
	private Double step;

	/**
	 * @param defaultValue
	 */
	public ThresholdElement(final Double defaultValue) {
		super(ThresholdElement.CLASS_ID, defaultValue);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement#clone()
	 */
	@Override
	public ThresholdElement clone() {
		ThresholdElement thresholdElement = new ThresholdElement(getValue());
		thresholdElement.setMaximum(getMaximum());
		thresholdElement.setMinimum(getMinimum());
		thresholdElement.setStep(getStep());
		return thresholdElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		final ThresholdElement element = (ThresholdElement) source;
		setMaximum(element.getMaximum());
		setMinimum(element.getMinimum());
		setValue(element.getValue());
	}

	/**
	 * @return the minimum
	 */
	public Double getMinimum() {
		return minimum;
	}

	/**
	 * @param minimum the minimum to set
	 */
	public void setMinimum(final Double minimum) {
		this.minimum = minimum;
	}

	/**
	 * @return the maximum
	 */
	public Double getMaximum() {
		return maximum;
	}

	/**
	 * @param maximum the maximum to set
	 */
	public void setMaximum(final Double maximum) {
		this.maximum = maximum;
	}

	/**
	 * @return the step
	 */
	public Double getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(final Double step) {
		this.step = step;
	}
}
