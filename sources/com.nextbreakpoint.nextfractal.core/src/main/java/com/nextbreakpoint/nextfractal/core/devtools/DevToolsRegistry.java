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
package com.nextbreakpoint.nextfractal.core.devtools;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extensionPoints.descriptor.DescriptorExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extensionPoints.descriptor.DescriptorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extensionPoints.processor.ProcessorExtensionRuntime;

/**
 * The twister registry.
 * 
 * @author Andrea Medeghini
 */
public class DevToolsRegistry {
	private ExtensionRegistry<ProcessorExtensionRuntime> processorRegistry;
	private ExtensionRegistry<DescriptorExtensionRuntime> descriptorRegistry;

	private static class RegistryHolder {
		private static final DevToolsRegistry instance = new DevToolsRegistry();
	}

	private DevToolsRegistry() {
		setProcessorRegistry(new ProcessorExtensionRegistry());
		setDescriptorRegistry(new DescriptorExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static DevToolsRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a processor extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ProcessorExtensionRuntime> getProcessorExtension(final String extensionId) throws ExtensionNotFoundException {
		return processorRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a descriptor extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<DescriptorExtensionRuntime> getDescriptorExtension(final String extensionId) throws ExtensionNotFoundException {
		return descriptorRegistry.getExtension(extensionId);
	}

	private void setProcessorRegistry(final ExtensionRegistry<ProcessorExtensionRuntime> processorRegistry) {
		this.processorRegistry = processorRegistry;
	}

	private void setDescriptorRegistry(final ExtensionRegistry<DescriptorExtensionRuntime> descriptorRegistry) {
		this.descriptorRegistry = descriptorRegistry;
	}

	/**
	 * @return the processorRegistry
	 */
	public ExtensionRegistry<ProcessorExtensionRuntime> getProcessorRegistry() {
		return processorRegistry;
	}

	/**
	 * @return the descriptorRegistry
	 */
	public ExtensionRegistry<DescriptorExtensionRuntime> getDescriptorRegistry() {
		return descriptorRegistry;
	}
}
