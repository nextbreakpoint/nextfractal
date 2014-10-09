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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer.PaletteRendererConfigElement;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractOutcolouringPaletteConfig extends AbstractOutcolouringFormulaConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_PALETTE_RENDERER_EXTENSION_ID = "twister.mandelbrot.palette.renderer.default";
	private PaletteRendererConfigElement paletteRendererElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		paletteRendererElement = new PaletteRendererConfigElement();
	}

	/**
	 * 
	 */
	@Override
	protected void initConfigElements() {
		try {
			paletteRendererElement.setReference(MandelbrotRegistry.getInstance().getPaletteRendererExtension(AbstractOutcolouringPaletteConfig.DEFAULT_PALETTE_RENDERER_EXTENSION_ID).createConfigurableExtensionReference());
		}
		catch (final Exception e) {
			throw new Error(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#getConfigElements()
	 */
	@Override
	public List<ConfigElement> getConfigElements() {
		final List<ConfigElement> elements = new ArrayList<ConfigElement>(1);
		elements.add(paletteRendererElement);
		return elements;
	}

	/**
	 * @return the rendererElement
	 */
	public ConfigurableExtensionReference<PaletteRendererExtensionConfig> getPaletteRenderer() {
		return paletteRendererElement.getReference();
	}

	/**
	 * @param paletteRendererElement the rendererElement to set
	 */
	public void setPaletteRenderer(final ConfigurableExtensionReference<PaletteRendererExtensionConfig> reference) {
		paletteRendererElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public PaletteRendererConfigElement getPaletteRendererElement() {
		return paletteRendererElement;
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
		final AbstractOutcolouringPaletteConfig other = (AbstractOutcolouringPaletteConfig) obj;
		if (paletteRendererElement == null) {
			if (other.paletteRendererElement != null) {
				return false;
			}
		}
		else if (!paletteRendererElement.equals(other.paletteRendererElement)) {
			return false;
		}
		return true;
	}
}
