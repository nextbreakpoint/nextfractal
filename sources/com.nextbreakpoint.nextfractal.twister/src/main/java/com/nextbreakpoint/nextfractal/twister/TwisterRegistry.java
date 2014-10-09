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
package com.nextbreakpoint.nextfractal.twister;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.converter.ConverterExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.converter.ConverterExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.effect.EffectExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.layerFilter.LayerFilterExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.layerFilter.LayerFilterExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.layerFilter.LayerFilterExtensionRuntime;

/**
 * The twister registry.
 * 
 * @author Andrea Medeghini
 */
public class TwisterRegistry {
	private ConfigurableExtensionRegistry<FrameFilterExtensionRuntime<?>, FrameFilterExtensionConfig> frameFilterRegistry;
	private ConfigurableExtensionRegistry<LayerFilterExtensionRuntime<?>, LayerFilterExtensionConfig> imageFilterRegistry;
	private ConfigurableExtensionRegistry<ImageExtensionRuntime<?>, ImageExtensionConfig> imageRegistry;
	private ConfigurableExtensionRegistry<EffectExtensionRuntime<?>, EffectExtensionConfig> effectRegistry;
	private ExtensionRegistry<ConverterExtensionRuntime> converterRegistry;

	private static class RegistryHolder {
		private static final TwisterRegistry instance = new TwisterRegistry();
	}

	private TwisterRegistry() {
		setFrameFilterRegistry(new FrameFilterExtensionRegistry());
		setLayerFilterRegistry(new LayerFilterExtensionRegistry());
		setImageRegistry(new ImageExtensionRegistry());
		setEffectRegistry(new EffectExtensionRegistry());
		setConverterRegistry(new ConverterExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static TwisterRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a filter extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<FrameFilterExtensionRuntime<?>, FrameFilterExtensionConfig> getFrameFilterExtension(final String extensionId) throws ExtensionNotFoundException {
		return frameFilterRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns an image extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<ImageExtensionRuntime<?>, ImageExtensionConfig> getImageExtension(final String extensionId) throws ExtensionNotFoundException {
		return imageRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns an image filter extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<LayerFilterExtensionRuntime<?>, LayerFilterExtensionConfig> getLayerFilterExtension(final String extensionId) throws ExtensionNotFoundException {
		return imageFilterRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns an effect extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<EffectExtensionRuntime<?>, EffectExtensionConfig> getEffectExtension(final String extensionId) throws ExtensionNotFoundException {
		return effectRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns a converter extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ConverterExtensionRuntime> getConverterExtension(final String extensionId) throws ExtensionNotFoundException {
		return converterRegistry.getExtension(extensionId);
	}

	private void setFrameFilterRegistry(final ConfigurableExtensionRegistry<FrameFilterExtensionRuntime<?>, FrameFilterExtensionConfig> frameFilterRegistry) {
		this.frameFilterRegistry = frameFilterRegistry;
	}

	private void setLayerFilterRegistry(final ConfigurableExtensionRegistry<LayerFilterExtensionRuntime<?>, LayerFilterExtensionConfig> imageFilterRegistry) {
		this.imageFilterRegistry = imageFilterRegistry;
	}

	private void setEffectRegistry(final ConfigurableExtensionRegistry<EffectExtensionRuntime<?>, EffectExtensionConfig> effectRegistry) {
		this.effectRegistry = effectRegistry;
	}

	private void setImageRegistry(final ConfigurableExtensionRegistry<ImageExtensionRuntime<?>, ImageExtensionConfig> imageRegistry) {
		this.imageRegistry = imageRegistry;
	}

	private void setConverterRegistry(final ExtensionRegistry<ConverterExtensionRuntime> converterRegistry) {
		this.converterRegistry = converterRegistry;
	}

	/**
	 * @return the frameFilterRegistry
	 */
	public ConfigurableExtensionRegistry<FrameFilterExtensionRuntime<?>, FrameFilterExtensionConfig> getFrameFilterRegistry() {
		return frameFilterRegistry;
	}

	/**
	 * @return the imageFilterRegistry
	 */
	public ConfigurableExtensionRegistry<LayerFilterExtensionRuntime<?>, LayerFilterExtensionConfig> getLayerFilterRegistry() {
		return imageFilterRegistry;
	}

	/**
	 * @return the effectRegistry
	 */
	public ConfigurableExtensionRegistry<EffectExtensionRuntime<?>, EffectExtensionConfig> getEffectRegistry() {
		return effectRegistry;
	}

	/**
	 * @return the imageRegistry
	 */
	public ConfigurableExtensionRegistry<ImageExtensionRuntime<?>, ImageExtensionConfig> getImageRegistry() {
		return imageRegistry;
	}

	/**
	 * @return the converterRegistry
	 */
	public ExtensionRegistry<ConverterExtensionRuntime> getConverterRegistry() {
		return converterRegistry;
	}
}
