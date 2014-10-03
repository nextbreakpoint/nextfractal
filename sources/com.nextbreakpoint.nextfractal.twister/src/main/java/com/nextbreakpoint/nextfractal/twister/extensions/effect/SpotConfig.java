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
package com.nextbreakpoint.nextfractal.twister.extensions.effect;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.common.ComplexElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class SpotConfig extends EffectExtensionConfig {
	private static final long serialVersionUID = 1L;
	private PercentageElement intensityElement;
	private ComplexElement centerElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		intensityElement = new PercentageElement(50);
		intensityElement.addChangeListener(new ValueChangeEventDispatcher());
		centerElement = new ComplexElement(new DoubleVector2D(0.5d, 0.5d));
		centerElement.addChangeListener(new ValueChangeEventDispatcher());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(intensityElement);
		elements.add(centerElement);
		return elements;
	}

	/**
	 * @return the intensity.
	 */
	public Integer getIntensity() {
		return intensityElement.getValue();
	}

	/**
	 * @param intensity the intensity to set.
	 */
	public void setIntensity(final Integer level) {
		intensityElement.setValue(level);
	}

	/**
	 * @return
	 */
	protected PercentageElement getIntensityElement() {
		return intensityElement;
	}

	/**
	 * @return the center.
	 */
	public DoubleVector2D getCenter() {
		return centerElement.getValue();
	}

	/**
	 * @param center the center to set.
	 */
	public void setCenter(final DoubleVector2D center) {
		centerElement.setValue(center);
	}

	/**
	 * @return
	 */
	protected ComplexElement getCenterElement() {
		return centerElement;
	}

	/**
	 * @return
	 */
	@Override
	public SpotConfig clone() {
		final SpotConfig config = new SpotConfig();
		config.setIntensity(getIntensity());
		config.setCenter(getCenter());
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
		if (obj == null) {
			return false;
		}
		final SpotConfig other = (SpotConfig) obj;
		if (centerElement == null) {
			if (other.centerElement != null) {
				return false;
			}
		}
		else if (!centerElement.equals(other.centerElement)) {
			return false;
		}
		if (intensityElement == null) {
			if (other.intensityElement != null) {
				return false;
			}
		}
		else if (!intensityElement.equals(other.intensityElement)) {
			return false;
		}
		return true;
	}

	private class ValueChangeEventDispatcher implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			fireValueChanged(e);
		}
	}
}
