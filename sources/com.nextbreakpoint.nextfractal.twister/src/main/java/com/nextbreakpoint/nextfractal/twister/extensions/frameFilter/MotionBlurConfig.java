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
package com.nextbreakpoint.nextfractal.twister.extensions.frameFilter;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class MotionBlurConfig extends FrameFilterExtensionConfig {
	private static final Integer DEFAULT_OPACITY = new Integer(70);
	private static final long serialVersionUID = 1L;
	private PercentageElement opacityElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		opacityElement = new PercentageElement(getDefaultOpacity());
		opacityElement.addChangeListener(new ValueChangeEventDispatcher());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(opacityElement);
		return elements;
	}

	/**
	 * @return the color.
	 */
	public Integer getOpacity() {
		return opacityElement.getValue();
	}

	/**
	 * @return the default color.
	 */
	public Integer getDefaultOpacity() {
		return MotionBlurConfig.DEFAULT_OPACITY;
	}

	/**
	 * @param opacity the color to set.
	 */
	public void setOpacity(final Integer opacity) {
		opacityElement.setValue(opacity);
	}

	/**
	 * @return
	 */
	public PercentageElement getOpacityElement() {
		return opacityElement;
	}

	/**
	 * @return
	 */
	@Override
	public MotionBlurConfig clone() {
		final MotionBlurConfig config = new MotionBlurConfig();
		config.setOpacity(getOpacity());
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
		final MotionBlurConfig other = (MotionBlurConfig) obj;
		if (opacityElement == null) {
			if (other.opacityElement != null) {
				return false;
			}
		}
		else if (!opacityElement.equals(other.opacityElement)) {
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
