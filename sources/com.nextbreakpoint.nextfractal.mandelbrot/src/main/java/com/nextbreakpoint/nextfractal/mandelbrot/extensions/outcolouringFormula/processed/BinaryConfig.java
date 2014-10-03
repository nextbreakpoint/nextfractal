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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.processed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfig;

/**
 * @author Andrea Medeghini
 */
public class BinaryConfig extends AbstractOutcolouringFormulaConfig {
	private static final long serialVersionUID = 1L;
	private static final Color32bit[] DEFAULT_COLORS = new Color32bit[] { new Color32bit(0xFF000000), new Color32bit(0xFFFFFFFF) };
	private ColorElement[] colorElements;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		colorElements = new ColorElement[2];
		colorElements[0] = new ColorElement(getDefaultColors()[0]);
		colorElements[1] = new ColorElement(getDefaultColors()[1]);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(colorElements[0]);
		elements.add(colorElements[1]);
		return elements;
	}

	/**
	 * @param colors
	 */
	public void setColors(final Color32bit[] colors) {
		colorElements[0].setValue(colors[0]);
		colorElements[1].setValue(colors[1]);
	}

	/**
	 * @return the colors.
	 */
	public Color32bit[] getColors() {
		return new Color32bit[] { colorElements[0].getValue(), colorElements[1].getValue() };
	}

	/**
	 * @return the default colors.
	 */
	public Color32bit[] getDefaultColors() {
		return BinaryConfig.DEFAULT_COLORS;
	}

	/**
	 * @param index
	 * @return
	 */
	protected ColorElement getColorElement(final int index) {
		return colorElements[index];
	}

	/**
	 * @return
	 */
	@Override
	public BinaryConfig clone() {
		final BinaryConfig config = new BinaryConfig();
		config.setColors(getColors());
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
		final BinaryConfig other = (BinaryConfig) obj;
		if (!Arrays.equals(colorElements, other.colorElements)) {
			return false;
		}
		return true;
	}
}
