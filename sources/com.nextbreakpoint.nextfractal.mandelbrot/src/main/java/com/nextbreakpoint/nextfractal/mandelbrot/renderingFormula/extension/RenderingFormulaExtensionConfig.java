/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.common.ComplexElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ThresholdElement;

/**
 * @author Andrea Medeghini
 */
public abstract class RenderingFormulaExtensionConfig extends ExtensionConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ThresholdElement thresholdElement;
	protected IterationsElement iterationsElement;
	protected ComplexElement centerElement;
	protected ComplexElement scaleElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		thresholdElement = new ThresholdElement(getDefaultThreshold());
		iterationsElement = new IterationsElement(getDefaultIterations());
		centerElement = new ComplexElement(getDefaultCenter());
		scaleElement = new ComplexElement(getDefaultScale());
		thresholdElement.setMaximum(getThresholdMax());
		thresholdElement.setMinimum(getThresholdMin());
		thresholdElement.setStep(getThresholdStep());
		iterationsElement.setMaximum(getIterationsMax());
		iterationsElement.setMinimum(getIterationsMin());
		iterationsElement.setStep(getIterationsStep());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(iterationsElement);
		elements.add(thresholdElement);
		elements.add(centerElement);
		elements.add(scaleElement);
		return elements;
	}

	/**
	 * @return
	 */
	public abstract Complex getDefaultConstant();

	/**
	 * @return
	 */
	public abstract Double getDefaultThreshold();

	/**
	 * @return
	 */
	public abstract Integer getDefaultIterations();

	/**
	 * @return
	 */
	public abstract DoubleVector2D getDefaultCenter();

	/**
	 * @return
	 */
	public abstract DoubleVector2D getDefaultScale();

	/**
	 * @return
	 */
	public boolean isMandelbrotModeAllowed() {
		return true;
	}

	/**
	 * @return the threshold
	 */
	public Double getThreshold() {
		return thresholdElement.getValue();
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(final Double threshold) {
		thresholdElement.setValue(threshold);
	}

	/**
	 * @return
	 */
	public Double getThresholdMin() {
		return 0.0;
	}

	/**
	 * @return
	 */
	public Double getThresholdMax() {
		return 1000.0;
	}

	/**
	 * @return
	 */
	public Double getThresholdStep() {
		return 0.1;
	}

	/**
	 * Returns the iterations.
	 * 
	 * @return the iterations.
	 */
	public Integer getIterations() {
		return iterationsElement.getValue();
	}

	/**
	 * Sets the iterations.
	 * 
	 * @param iterations the iterations to set
	 */
	public void setIterations(final Integer iterations) {
		iterationsElement.setValue(iterations);
	}

	/**
	 * @return
	 */
	public Integer getIterationsMin() {
		return 1;
	}

	/**
	 * @return
	 */
	public Integer getIterationsMax() {
		return 10000;
	}

	/**
	 * @return
	 */
	public Integer getIterationsStep() {
		return 1;
	}

	/**
	 * @return
	 */
	public DoubleVector2D getCenter() {
		return centerElement.getValue();
	}

	/**
	 * @param center
	 */
	public void setCenter(final DoubleVector2D center) {
		centerElement.setValue(center);
	}

	/**
	 * @return
	 */
	public DoubleVector2D getScale() {
		return scaleElement.getValue();
	}

	/**
	 * @param scale
	 */
	public void setScale(final DoubleVector2D scale) {
		scaleElement.setValue(scale);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return dump(new StringBuilder()).toString();
	}

	/**
	 * @param buffer
	 * @return
	 */
	protected StringBuilder dump(final StringBuilder buffer) {
		buffer.append("threshold = ");
		buffer.append(getThreshold());
		buffer.append(", iterations = ");
		buffer.append(getIterations());
		buffer.append(", center = ");
		buffer.append(getCenter());
		buffer.append(", scale = ");
		buffer.append(getScale());
		return buffer;
	}

	/**
	 * @return
	 */
	public IterationsElement getIterationsElement() {
		return iterationsElement;
	}

	/**
	 * @return
	 */
	public ThresholdElement getThresholdElement() {
		return thresholdElement;
	}

	/**
	 * @return
	 */
	public ComplexElement getCenterElement() {
		return centerElement;
	}

	/**
	 * @return
	 */
	public ComplexElement getScaleElement() {
		return scaleElement;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final RenderingFormulaExtensionConfig other = (RenderingFormulaExtensionConfig) obj;
		if (centerElement == null) {
			if (other.centerElement != null) {
				return false;
			}
		}
		else if (!centerElement.equals(other.centerElement)) {
			return false;
		}
		if (iterationsElement == null) {
			if (other.iterationsElement != null) {
				return false;
			}
		}
		else if (!iterationsElement.equals(other.iterationsElement)) {
			return false;
		}
		if (scaleElement == null) {
			if (other.scaleElement != null) {
				return false;
			}
		}
		else if (!scaleElement.equals(other.scaleElement)) {
			return false;
		}
		if (thresholdElement == null) {
			if (other.thresholdElement != null) {
				return false;
			}
		}
		else if (!thresholdElement.equals(other.thresholdElement)) {
			return false;
		}
		return true;
	}
}
