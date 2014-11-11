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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.paletteRenderer;

import com.nextbreakpoint.nextfractal.core.util.Palette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPalette;
import com.nextbreakpoint.nextfractal.twister.elements.PaletteElement;

/**
 * @author Andrea Medeghini
 */
public final class GrayGradientRendererConfig extends AbstractPaletteRendererConfig {
	private static final long serialVersionUID = 1L;
	private final Palette palette = getDefaultPalette();
	private final PaletteElement<Palette> paletteElement = new PaletteElement<Palette>("Palette", palette);

	/**
	 * @return
	 */
	@Override
	public Palette getPalette() {
		return palette;
	}

	/**
	 * @return
	 */
	public final Palette getDefaultPalette() {
		return new DefaultRenderedPalette(0xFF000000, 0xFFFFFFFF);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionConfig#getPaletteElement()
	 */
	@Override
	public PaletteElement<?> getPaletteElement() {
		return paletteElement;
	}

	/**
	 * @return
	 */
	@Override
	public GrayGradientRendererConfig clone() {
		final GrayGradientRendererConfig config = new GrayGradientRendererConfig();
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
		return true;
	}
}
