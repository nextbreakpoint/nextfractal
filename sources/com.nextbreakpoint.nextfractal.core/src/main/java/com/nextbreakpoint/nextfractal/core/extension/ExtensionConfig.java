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
package com.nextbreakpoint.nextfractal.core.extension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;

/**
 * Interface of extension configurations.
 * 
 * @author Andrea Medeghini
 */
public abstract class ExtensionConfig implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private ConfigurableExtensionReference<?> reference;
	private transient List<ValueChangeListener> listenerList;
	private transient ConfigContext context;

	/**
	 * 
	 */
	public ExtensionConfig() {
		createConfigElements();
		initConfigElements();
		final List<ConfigElement> elements = getConfigElements();
		for (final ConfigElement element : elements) {
			element.addChangeListener(new EventDispatcher());
		}
	}

	/**
	 * @throws Throwable
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * 
	 */
	public void dispose() {
		if (reference != null) {
			if (reference.getExtensionConfig() != null) {
				reference.getExtensionConfig().dispose();
			}
			reference = null;
		}
		context = null;
		if (listenerList != null) {
			listenerList.clear();
			listenerList = null;
		}
		disposeConfigElements();
	}

	private List<ValueChangeListener> getListenerList() {
		if (listenerList == null) {
			listenerList = new ArrayList<ValueChangeListener>(1);
		}
		return listenerList;
	}

	/**
	 * Sets the configuration context.
	 * 
	 * @param context the configuration context to set.
	 */
	public void setContext(final ConfigContext context) {
		this.context = context;
		final List<ConfigElement> elements = getConfigElements();
		for (final ConfigElement element : elements) {
			element.setContext(context);
		}
	}

	/**
	 * Returns the configuration context.
	 * 
	 * @return the configuration context.
	 */
	public ConfigContext getContext() {
		if (context == null) {
			throw new IllegalStateException("Context not defined");
		}
		return context;
	}

	/**
	 * Called to create the configuration elements.
	 */
	protected void createConfigElements() {
	}

	/**
	 * Called to dispose the configuration elements.
	 */
	protected void disposeConfigElements() {
		final List<ConfigElement> elements = getConfigElements();
		for (final ConfigElement element : elements) {
			element.dispose();
		}
	}

	/**
	 * Called to initialize the configuration elements.
	 */
	protected void initConfigElements() {
	}

	/**
	 * Returns the extension reference.
	 * 
	 * @return the extension reference.
	 */
	public ConfigurableExtensionReference<?> getExtensionReference() {
		return reference;
	}

	/**
	 * Sets the extension reference.
	 * 
	 * @param reference the extension reference to set.
	 */
	void setExtensionReference(final ConfigurableExtensionReference<?> reference) {
		this.reference = reference;
	}

	/**
	 * Returns the extensionId.
	 * 
	 * @return the extensionId.
	 */
	public String getExtensionId() {
		return reference.getExtensionId();
	}

	/**
	 * Returns the elements list.
	 * 
	 * @return the elements list.
	 */
	public List<ConfigElement> getConfigElements() {
		return new ArrayList<ConfigElement>(0);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract ExtensionConfig clone();

	/**
	 * Add a change listener.
	 * 
	 * @param listener the listener.
	 */
	public void addChangeListener(final ValueChangeListener listener) {
		getListenerList().add(listener);
	}

	/**
	 * Remove a change listener.
	 * 
	 * @param listener the listener.
	 */
	public void removeChangeListener(final ValueChangeListener listener) {
		getListenerList().remove(listener);
	}

	/**
	 * Fires a new event.
	 * 
	 * @param e event to fire.
	 */
	protected void fireValueChanged(final ValueChangeEvent e) {
		final List<ValueChangeListener> listeners = getListenerList();
		for (final ValueChangeListener listener : listeners) {
			listener.valueChanged(e);
		}
	}

	private class EventDispatcher implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
		 */
		@Override
		public void valueChanged(final ValueChangeEvent e) {
			fireValueChanged(e);
		}
	}
}
