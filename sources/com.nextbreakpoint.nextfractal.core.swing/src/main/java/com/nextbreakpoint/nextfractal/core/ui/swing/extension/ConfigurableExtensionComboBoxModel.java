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
package com.nextbreakpoint.nextfractal.core.ui.swing.extension;

import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionComparator;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.NullConfigurableExtension;

/**
 * A model for extensions lists.
 * 
 * @author Andrea Medeghini
 */
public class ConfigurableExtensionComboBoxModel implements ComboBoxModel {
	private static final long serialVersionUID = 1L;
	private final DefaultComboBoxModel model = new DefaultComboBoxModel();
	private final ConfigurableExtensionRegistry<?, ?> registry;
	private final boolean isNullExtensionAllowed;

	/**
	 * Constructs a new model.
	 * 
	 * @param registry the extension registry.
	 * @param isNullExtensionAllowed true if null extension is allowed
	 */
	@SuppressWarnings("unchecked")
	public ConfigurableExtensionComboBoxModel(final ConfigurableExtensionRegistry registry, final boolean isNullExtensionAllowed) {
		this(registry, null, isNullExtensionAllowed);
	}

	/**
	 * Constructs a new model.
	 * 
	 * @param registry the extension registry.
	 * @param filter the extension filter
	 * @param isNullExtensionAllowed true if null extension is allowed
	 */
	@SuppressWarnings("unchecked")
	public ConfigurableExtensionComboBoxModel(final ConfigurableExtensionRegistry registry, final ExtensionFilter filter, final boolean isNullExtensionAllowed) {
		if (registry == null) {
			throw new NullPointerException("registry == null");
		}
		this.isNullExtensionAllowed = isNullExtensionAllowed;
		reload(registry, filter);
		this.registry = registry;
	}

	/**
	 * @param registry
	 * @param filter
	 */
	@SuppressWarnings("unchecked")
	public void reload(final ExtensionRegistry registry, final ExtensionFilter filter) {
		model.removeAllElements();
		final List<ConfigurableExtension<?, ?>> extensions = registry.getExtensionList();
		Collections.sort(extensions, new ExtensionComparator());
		if (isNullExtensionAllowed) {
			model.addElement(NullConfigurableExtension.getInstance());
		}
		for (final ConfigurableExtension extension : extensions) {
			if ((filter == null) || filter.accept(extension)) {
				model.addElement(extension);
			}
		}
	}

	/**
	 * @param extensionId
	 */
	public void setSelectedItemByExtensionId(final String extensionId) {
		try {
			setSelectedItem(registry.getExtension(extensionId));
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	@Override
	public void setSelectedItem(final Object item) {
		model.setSelectedItem(item);
	}

	/**
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	@Override
	public Object getSelectedItem() {
		return model.getSelectedItem();
	}

	/**
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return model.getSize();
	}

	/**
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(final int index) {
		return model.getElementAt(index);
	}

	/**
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void addListDataListener(final ListDataListener listener) {
		model.addListDataListener(listener);
	}

	/**
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	@Override
	public void removeListDataListener(final ListDataListener listener) {
		model.removeListDataListener(listener);
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionRegistry<?, ?> getRegistry() {
		return registry;
	}
}
