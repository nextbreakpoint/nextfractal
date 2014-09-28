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
package com.nextbreakpoint.nextfractal.twister.extensions.image;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class ColorConfig extends ImageExtensionConfig {
	private static final Color32bit DEFAULT_COLOR = Color32bit.BLACK;
	private static final long serialVersionUID = 1L;
	private ColorElement colorElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		colorElement = new ColorElement(getDefaultColor());
		colorElement.addChangeListener(new ValueChangeEventDispatcher());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(colorElement);
		return elements;
	}

	/**
	 * @return the color.
	 */
	public Color32bit getColor() {
		return colorElement.getValue();
	}

	/**
	 * @return the default color.
	 */
	public Color32bit getDefaultColor() {
		return ColorConfig.DEFAULT_COLOR;
	}

	/**
	 * @param color the color to set.
	 */
	public void setColor(final Color32bit color) {
		colorElement.setValue(color);
	}

	/**
	 * @return
	 */
	protected ColorElement getColorElement() {
		return colorElement;
	}

	/**
	 * @return
	 */
	@Override
	public ColorConfig clone() {
		final ColorConfig config = new ColorConfig();
		config.setColor(getColor());
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
		final ColorConfig other = (ColorConfig) obj;
		if (colorElement == null) {
			if (other.colorElement != null) {
				return false;
			}
		}
		else if (!colorElement.equals(other.colorElement)) {
			return false;
		}
		return true;
	}

	private class ValueChangeEventDispatcher implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			fireValueChanged(e);
		}
	}
}
