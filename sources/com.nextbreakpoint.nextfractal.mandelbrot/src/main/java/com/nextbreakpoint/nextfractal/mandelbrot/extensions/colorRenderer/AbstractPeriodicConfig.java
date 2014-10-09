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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.colorRenderer;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.ColorRendererFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRendererFormula.ColorRendererFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractPeriodicConfig extends AbstractColorRendererConfig {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_COLOR_RENDERER_FORMULA_EXTENSION_ID = "twister.mandelbrot.color.renderer.formula.log";
	private DoubleElement scaleElement;
	private DoubleElement frequencyElement;
	private PercentageElement amplitudeElement;
	private BooleanElement timeEnabledElement;
	private BooleanElement absoluteEnabledElement;
	private ColorRendererFormulaConfigElement rendererFormulaElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		rendererFormulaElement = new ColorRendererFormulaConfigElement();
		scaleElement = new DoubleElement(getDefaultScale());
		frequencyElement = new DoubleElement(getDefaultFrequency());
		amplitudeElement = new PercentageElement(getDefaultAmplitude());
		timeEnabledElement = new BooleanElement(getDefaultTimeEnabled());
		absoluteEnabledElement = new BooleanElement(getDefaultAbsoluteEnabled());
	}

	/**
	 * 
	 */
	@Override
	protected void initConfigElements() {
		rendererFormulaElement.setReference(getDefaultColorRendererFormula());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(scaleElement);
		elements.add(frequencyElement);
		elements.add(amplitudeElement);
		elements.add(timeEnabledElement);
		elements.add(absoluteEnabledElement);
		elements.add(rendererFormulaElement);
		return elements;
	}

	/**
	 * @return
	 */
	public Integer getAmplitude() {
		return amplitudeElement.getValue();
	}

	/**
	 * @return
	 */
	public Integer getDefaultAmplitude() {
		return new Integer(100);
	}

	/**
	 * @param amplitude
	 */
	public void setAmplitude(final Integer amplitude) {
		amplitudeElement.setValue(amplitude);
	}

	/**
	 * @return
	 */
	public Double getFrequency() {
		return frequencyElement.getValue();
	}

	/**
	 * @return
	 */
	public Double getDefaultFrequency() {
		return new Double(1);
	}

	/**
	 * @param frequency
	 */
	public void setFrequency(final Double frequency) {
		frequencyElement.setValue(frequency);
	}

	/**
	 * @return
	 */
	public Double getScale() {
		return scaleElement.getValue();
	}

	/**
	 * @return
	 */
	public Double getDefaultScale() {
		return new Double(100);
	}

	/**
	 * @param scale
	 */
	public void setScale(final Double scale) {
		scaleElement.setValue(scale);
	}

	/**
	 * @return
	 */
	public Boolean getAbsoluteEnabled() {
		return absoluteEnabledElement.getValue();
	}

	/**
	 * @return
	 */
	public Boolean getDefaultAbsoluteEnabled() {
		return false;
	}

	/**
	 * @param absoluteEnabled
	 */
	public void setAbsoluteEnabled(final Boolean absoluteEnabled) {
		absoluteEnabledElement.setValue(absoluteEnabled);
	}

	/**
	 * @return
	 */
	public Boolean getTimeEnabled() {
		return timeEnabledElement.getValue();
	}

	/**
	 * @return
	 */
	public Boolean getDefaultTimeEnabled() {
		return true;
	}

	/**
	 * @param timeEnabled
	 */
	public void setTimeEnabled(final Boolean timeEnabled) {
		timeEnabledElement.setValue(timeEnabled);
	}

	/**
	 * @return
	 */
	public ExtensionReference getColorRendererFormula() {
		return rendererFormulaElement.getReference();
	}

	/**
	 * @return
	 */
	public ExtensionReference getDefaultColorRendererFormula() {
		try {
			final Extension<ColorRendererFormulaExtensionRuntime> extension = MandelbrotRegistry.getInstance().getColorRendererFormulaExtension(AbstractPeriodicConfig.DEFAULT_COLOR_RENDERER_FORMULA_EXTENSION_ID);
			final ExtensionReference reference = extension.getExtensionReference();
			return reference;
		}
		catch (final Exception e) {
			throw new Error(e);
		}
	}

	/**
	 * @param formula
	 */
	public void setColorRendererFormula(final ExtensionReference formula) {
		rendererFormulaElement.setReference(formula);
	}

	/**
	 * @return the rendererFormula
	 */
	public ColorRendererFormulaConfigElement getColorRendererFormulaElement() {
		return rendererFormulaElement;
	}

	/**
	 * @return
	 */
	public PercentageElement getAmplitudeElement() {
		return amplitudeElement;
	}

	/**
	 * @return
	 */
	public DoubleElement getFrequencyElement() {
		return frequencyElement;
	}

	/**
	 * @return
	 */
	public DoubleElement getScaleElement() {
		return scaleElement;
	}

	/**
	 * @return
	 */
	public BooleanElement getTimeEnabledElement() {
		return timeEnabledElement;
	}

	/**
	 * @return
	 */
	public BooleanElement getAbsoluteEnabledElement() {
		return absoluteEnabledElement;
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
		final AbstractPeriodicConfig other = (AbstractPeriodicConfig) obj;
		if (absoluteEnabledElement == null) {
			if (other.absoluteEnabledElement != null) {
				return false;
			}
		}
		else if (!absoluteEnabledElement.equals(other.absoluteEnabledElement)) {
			return false;
		}
		if (amplitudeElement == null) {
			if (other.amplitudeElement != null) {
				return false;
			}
		}
		else if (!amplitudeElement.equals(other.amplitudeElement)) {
			return false;
		}
		if (frequencyElement == null) {
			if (other.frequencyElement != null) {
				return false;
			}
		}
		else if (!frequencyElement.equals(other.frequencyElement)) {
			return false;
		}
		if (rendererFormulaElement == null) {
			if (other.rendererFormulaElement != null) {
				return false;
			}
		}
		else if (!rendererFormulaElement.equals(other.rendererFormulaElement)) {
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
		if (timeEnabledElement == null) {
			if (other.timeEnabledElement != null) {
				return false;
			}
		}
		else if (!timeEnabledElement.equals(other.timeEnabledElement)) {
			return false;
		}
		return true;
	}
}
