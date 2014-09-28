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
package com.nextbreakpoint.nextfractal.twister;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerConfigElement;

/**
 * @author Andrea Medeghini
 */
public class TwisterConfigBuilder {
	private static final String DEFAULT_IMAGE_EXTENSION_ID = "twister.frame.layer.image.mandelbrot";

	/**
	 * Constructs a new builder.
	 */
	public TwisterConfigBuilder() {
	}

	/**
	 * Returns the default config.
	 * 
	 * @return the default config.
	 * @throws ExtensionException
	 * @throws ExtensionNotFoundException
	 */
	public TwisterConfig createDefaultConfig() throws ExtensionNotFoundException, ExtensionException {
		final TwisterConfig config = new TwisterConfig();
		final FrameConfigElement frameElement = new FrameConfigElement();
		final GroupLayerConfigElement groupLayerElement = new GroupLayerConfigElement();
		final ImageLayerConfigElement imageLayerElement = new ImageLayerConfigElement();
		final ImageConfigElement imageElement = new ImageConfigElement();
		final EffectConfigElement effectElement = new EffectConfigElement();
		config.setFrameConfigElement(frameElement);
		frameElement.appendLayerConfigElement(groupLayerElement);
		groupLayerElement.appendLayerConfigElement(imageLayerElement);
		imageLayerElement.setImageConfigElement(imageElement);
		imageElement.setReference(TwisterRegistry.getInstance().getImageExtension(TwisterConfigBuilder.DEFAULT_IMAGE_EXTENSION_ID).createConfigurableExtensionReference());
		config.setEffectConfigElement(effectElement);
		return config;
	}
}
