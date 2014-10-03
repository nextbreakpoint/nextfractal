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
package com.nextbreakpoint.nextfractal.core.extension.sl;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.CoreResources;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionComparator;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionDescriptor;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime;

/**
 * SL extension registry.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 */
public class SLExtensionRegistry<T extends ExtensionRuntime> implements ExtensionRegistry<T> {
	private static final Logger logger = Logger.getLogger(SLExtensionRegistry.class.getName());
	private final Hashtable<String, Extension<T>> extensionMap = new Hashtable<String, Extension<T>>();
	private Class<? extends ExtensionDescriptor<T>> extensionDescriptorClass;
	private String extensionPointName;
	private String cfgElementName;
	
	/**
	 * Constructs a new extension registry.
	 * @param descriptorClass the extension descriptor class.
	 * @param extensionPointName the extension point name.
	 * @param builder the extension builder.
	 */
	protected SLExtensionRegistry(final Class<? extends ExtensionDescriptor<T>> extensionDescriptorClass, final String extensionPointName, final SLExtensionBuilder<T> builder) {
		this.extensionDescriptorClass = extensionDescriptorClass;
		this.extensionPointName = extensionPointName;
		this.cfgElementName = builder.getCfgElementName();
		final ServiceLoader<? extends ExtensionDescriptor<T>> serviceLoader = ServiceLoader.load(extensionDescriptorClass);
		for (ExtensionDescriptor<T> extensionDescriptor : serviceLoader) {
			logger.fine(extensionDescriptor.getExtensionId());
			try {
				Extension<T> extension = builder.createExtension(extensionDescriptor);
				this.extensionMap.put(extension.getExtensionId(), extension);
			} catch (SLExtensionBuilderException e) {
				SLExtensionRegistry.logger.log(Level.WARNING, MessageFormat.format(CoreResources.getInstance().getString("extension.error"), extensionDescriptor.getExtensionName()), e);
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry#getExtensionList()
	 */
	@Override
	public List<Extension<T>> getExtensionList() {
		final Enumeration<Extension<T>> elements = this.extensionMap.elements();
		final List<Extension<T>> list = new LinkedList<Extension<T>>();
		while (elements.hasMoreElements()) {
			list.add(elements.nextElement());
		}
		Collections.sort(list, new ExtensionComparator());
		return list;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry#getExtension(java.lang.String)
	 */
	@Override
	public Extension<T> getExtension(final String extensionId) throws ExtensionNotFoundException {
		final Extension<T> extension = this.extensionMap.get(extensionId);
		if (extension == null) {
			throw new ExtensionNotFoundException("Can't find extension " + extensionId + " [cfgElementName = " + cfgElementName + ", extensionPointName = " + extensionPointName + "]");
		}
		return extension;
	}

	/**
	 * Returns the extension descriptor class.
	 * @return the extension descriptor class.
	 */
	protected Class<? extends ExtensionDescriptor<T>> getExtensionDescriptorClass() {
		return extensionDescriptorClass;
	}
}
