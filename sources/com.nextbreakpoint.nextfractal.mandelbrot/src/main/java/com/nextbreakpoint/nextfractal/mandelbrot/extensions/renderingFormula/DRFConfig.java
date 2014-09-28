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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula;

import java.util.List;

import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;

/**
 * @author Andrea Medeghini
 */
public class DRFConfig extends AbstractRenderingFormulaConfig {
	private static final long serialVersionUID = 1L;
	private DoubleElement exponentElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		super.createConfigElements();
		exponentElement = new DoubleElement(getDefaultExponent());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = super.getConfigElements();
		elements.add(exponentElement);
		return elements;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultThreshold()
	 */
	@Override
	public Double getDefaultThreshold() {
		return 0.000001d;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultIterations()
	 */
	@Override
	public Integer getDefaultIterations() {
		return 200;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultCenter()
	 */
	@Override
	public DoubleVector2D getDefaultCenter() {
		return new DoubleVector2D(0, 0);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultScale()
	 */
	@Override
	public DoubleVector2D getDefaultScale() {
		return new DoubleVector2D(5.0, 5.0);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getDefaultConstant()
	 */
	@Override
	public Complex getDefaultConstant() {
		return new Complex(0.0, 0.0);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getThresholdMin()
	 */
	@Override
	public Double getThresholdMin() {
		return 0.0000001d;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getThresholdMax()
	 */
	@Override
	public Double getThresholdMax() {
		return 1d;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getThresholdStep()
	 */
	@Override
	public Double getThresholdStep() {
		return 0.0000001d;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getIterationsMin()
	 */
	@Override
	public Integer getIterationsMin() {
		return 1;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getIterationsMax()
	 */
	@Override
	public Integer getIterationsMax() {
		return 10000;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig#getIterationsStep()
	 */
	@Override
	public Integer getIterationsStep() {
		return 1;
	}

	/**
	 * @return
	 */
	public Double getDefaultExponent() {
		return 2d;
	}

	/**
	 * @return
	 */
	public Double getExponent() {
		return exponentElement.getValue();
	}

	/**
	 * @param exponent
	 */
	public void setExponent(final Double exponent) {
		exponentElement.setValue(exponent);
	}

	/**
	 * @return
	 */
	public Double getExponentMin() {
		return -100d;
	}

	/**
	 * @return
	 */
	public Double getExponentMax() {
		return 100d;
	}

	/**
	 * @return
	 */
	public Double getExponentStep() {
		return 0.1d;
	}

	/**
	 * @param buffer
	 * @return
	 */
	@Override
	protected StringBuilder dump(final StringBuilder buffer) {
		super.dump(buffer);
		buffer.append(", exponent = ");
		buffer.append(getExponent());
		return buffer;
	}

	/**
	 * @return
	 */
	public DoubleElement getExponentElement() {
		return exponentElement;
	}

	/**
	 * @return
	 */
	@Override
	public DRFConfig clone() {
		final DRFConfig config = new DRFConfig();
		config.setCenter(getCenter());
		config.setScale(getScale());
		config.setIterations(getIterations());
		config.setThreshold(getThreshold());
		config.setExponent(getExponent());
		return config;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		final DRFConfig other = (DRFConfig) obj;
		if (exponentElement == null) {
			if (other.exponentElement != null) {
				return false;
			}
		}
		else if (!exponentElement.equals(other.exponentElement)) {
			return false;
		}
		return true;
	}
}
