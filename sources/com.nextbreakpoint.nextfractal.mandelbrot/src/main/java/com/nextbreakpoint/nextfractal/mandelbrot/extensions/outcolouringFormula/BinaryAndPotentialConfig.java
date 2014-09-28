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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula;

import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;

/**
 * @author Andrea Medeghini
 */
public class BinaryAndPotentialConfig extends AbstractOutcolouringPaletteConfig {
	private static final long serialVersionUID = 1L;
	private static final Integer DEFAULT_OFFSET = new Integer(50);
	private PercentageElement offsetElement;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = super.getConfigElements();
		elements.add(offsetElement);
		return elements;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringPaletteConfig#initConfigElements()
	 */
	@Override
	protected void initConfigElements() {
		offsetElement = new PercentageElement(getDefaultOffset());
		super.initConfigElements();
	}

	/**
	 * @param offset
	 */
	public void setOffset(final Integer offset) {
		offsetElement.setValue(offset);
	}

	/**
	 * @return the offset.
	 */
	public Integer getOffset() {
		return offsetElement.getValue();
	}

	/**
	 * @return the default offset.
	 */
	public Integer getDefaultOffset() {
		return BinaryAndPotentialConfig.DEFAULT_OFFSET;
	}

	/**
	 * @return
	 */
	public PercentageElement getOffsetElement() {
		return offsetElement;
	}

	/**
	 * @return
	 */
	@Override
	public BinaryAndPotentialConfig clone() {
		final BinaryAndPotentialConfig config = new BinaryAndPotentialConfig();
		config.setPaletteRenderer(getPaletteRenderer().clone());
		config.setOffset(getOffset());
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
		final BinaryAndPotentialConfig other = (BinaryAndPotentialConfig) obj;
		if (offsetElement == null) {
			if (other.offsetElement != null) {
				return false;
			}
		}
		else if (!offsetElement.equals(other.offsetElement)) {
			return false;
		}
		return true;
	}
}
