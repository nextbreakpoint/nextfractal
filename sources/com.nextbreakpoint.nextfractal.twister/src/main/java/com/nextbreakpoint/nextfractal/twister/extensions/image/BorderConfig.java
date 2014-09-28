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
import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class BorderConfig extends ImageExtensionConfig {
	private static final Color32bit DEFAULT_COLOR = Color32bit.BLACK;
	private static final Double DEFAULT_SIZE = new Double(2.5);
	private static final long serialVersionUID = 1L;
	private ColorElement colorElement;
	private DoubleElement sizeElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		colorElement = new ColorElement(getDefaultColor());
		sizeElement = new DoubleElement(getDefaultSize());
		colorElement.addChangeListener(new ValueChangeEventDispatcher());
		sizeElement.addChangeListener(new ValueChangeEventDispatcher());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(colorElement);
		elements.add(sizeElement);
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
		return BorderConfig.DEFAULT_COLOR;
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
	 * @return the size.
	 */
	public Double getSize() {
		return sizeElement.getValue();
	}

	/**
	 * @return the default size.
	 */
	public Double getDefaultSize() {
		return BorderConfig.DEFAULT_SIZE;
	}

	/**
	 * @param size the size to set.
	 */
	public void setSize(final Double size) {
		sizeElement.setValue(size);
	}

	/**
	 * @return
	 */
	protected DoubleElement getSizeElement() {
		return sizeElement;
	}

	/**
	 * @return
	 */
	@Override
	public BorderConfig clone() {
		final BorderConfig config = new BorderConfig();
		config.setColor(getColor());
		config.setSize(getSize());
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
		final BorderConfig other = (BorderConfig) obj;
		if (colorElement == null) {
			if (other.colorElement != null) {
				return false;
			}
		}
		else if (!colorElement.equals(other.colorElement)) {
			return false;
		}
		if (sizeElement == null) {
			if (other.sizeElement != null) {
				return false;
			}
		}
		else if (!sizeElement.equals(other.sizeElement)) {
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
