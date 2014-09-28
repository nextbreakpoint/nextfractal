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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.common.RenderedPaletteElement;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;
import com.nextbreakpoint.nextfractal.twister.common.PaletteElement;

/**
 * @author Andrea Medeghini
 */
public class DefaultRendererConfig extends AbstractPaletteRendererConfig {
	private static final long serialVersionUID = 1L;
	private RenderedPaletteElement paletteElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		paletteElement = new RenderedPaletteElement(getDefaultRenderedPalette());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(paletteElement);
		return elements;
	}

	/**
	 * @return
	 */
	@Override
	public Palette getPalette() {
		return paletteElement.getValue();
	}

	/**
	 * @return
	 */
	public RenderedPalette getRenderedPalette() {
		return paletteElement.getValue();
	}

	/**
	 * @return
	 */
	public RenderedPalette getDefaultRenderedPalette() {
		return new DefaultRenderedPalette();
	}

	/**
	 * @param palette
	 */
	public void setRenderedPalette(final RenderedPalette palette) {
		paletteElement.setValue(palette);
	}

	/**
	 * @return the paletteElement
	 */
	@Override
	public PaletteElement<?> getPaletteElement() {
		return paletteElement;
	}

	/**
	 * @return the paletteElement
	 */
	public RenderedPaletteElement getRenderedPaletteElement() {
		return paletteElement;
	}

	/**
	 * @return
	 */
	@Override
	public DefaultRendererConfig clone() {
		final DefaultRendererConfig config = new DefaultRendererConfig();
		config.setRenderedPalette(getRenderedPalette());
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
		final DefaultRendererConfig other = (DefaultRendererConfig) obj;
		if (paletteElement == null) {
			if (other.paletteElement != null) {
				return false;
			}
		}
		else if (!paletteElement.equals(other.paletteElement)) {
			return false;
		}
		return true;
	}
}
