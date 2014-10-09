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

import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer.ColorRendererConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class UniversalConfig extends AbstractOutcolouringPaletteConfig {
	private static final String DEFAULT_COLOR_RENDERER_EXTENSION_ID = "twister.mandelbrot.color.renderer.sin";
	private static final long serialVersionUID = 1L;
	private ColorRendererConfigElement colorRendererElement;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		super.createConfigElements();
		colorRendererElement = new ColorRendererConfigElement();
	}

	/**
	 * 
	 */
	@Override
	protected void initConfigElements() {
		super.initConfigElements();
		try {
			colorRendererElement.setReference(MandelbrotRegistry.getInstance().getColorRendererExtension(UniversalConfig.DEFAULT_COLOR_RENDERER_EXTENSION_ID).createConfigurableExtensionReference());
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
		final List<ConfigElement> elements = super.getConfigElements();
		elements.add(colorRendererElement);
		return elements;
	}

	/**
	 * @return the colorRenderer
	 */
	public ConfigurableExtensionReference<ColorRendererExtensionConfig> getColorRenderer() {
		return colorRendererElement.getReference();
	}

	/**
	 * @param colorRendererElement the colorRenderer to set
	 */
	public void setColorRenderer(final ConfigurableExtensionReference<ColorRendererExtensionConfig> reference) {
		colorRendererElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public ColorRendererConfigElement getColorRendererElement() {
		return colorRendererElement;
	}

	/**
	 * @return
	 */
	@Override
	public UniversalConfig clone() {
		final UniversalConfig config = new UniversalConfig();
		config.setPaletteRenderer(getPaletteRenderer().clone());
		config.setColorRenderer(getColorRenderer().clone());
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
		final UniversalConfig other = (UniversalConfig) obj;
		if (colorRendererElement == null) {
			if (other.colorRendererElement != null) {
				return false;
			}
		}
		else if (!colorRendererElement.equals(other.colorRendererElement)) {
			return false;
		}
		return true;
	}
}
