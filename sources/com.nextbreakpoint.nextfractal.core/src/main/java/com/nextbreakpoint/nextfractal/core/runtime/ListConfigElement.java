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
package com.nextbreakpoint.nextfractal.core.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public class ListConfigElement<T extends ConfigElement> extends AbstractConfigElement {
	/**
	 * 
	 */
	public static final int ELEMENT_INSERTED_AFTER = 10;
	/**
	 * 
	 */
	public static final int ELEMENT_INSERTED_BEFORE = 11;
	/**
	 * 
	 */
	public static final int ELEMENT_REMOVED = 12;
	/**
	 * 
	 */
	public static final int ELEMENT_ADDED = 13;
	/**
	 * 
	 */
	public static final int ELEMENT_MOVED_UP = 14;
	/**
	 * 
	 */
	public static final int ELEMENT_MOVED_DOWN = 15;
	/**
	 * 
	 */
	public static final int ELEMENT_CHANGED = 16;
	private static final long serialVersionUID = 1L;
	private List<T> elements;

	/**
	 * 
	 */
	public ListConfigElement(final String configElementId) {
		this(configElementId, 0);
	}

	/**
	 * 
	 */
	public ListConfigElement(final String configElementId, final int size) {
		super(configElementId);
		elements = new ArrayList<T>(size);
	}

	/**
	 * Returns a element.
	 * 
	 * @param index the index.
	 * @return the layer.
	 */
	public T getElement(final int index) {
		return elements.get(index);
	}

	/**
	 * Returns a element index.
	 * 
	 * @param element the element.
	 * @return the index.
	 */
	public int indexOfElement(final T element) {
		return elements.indexOf(element);
	}

	/**
	 * Returns the number of elements.
	 * 
	 * @return the number of elements.
	 */
	public int getElementCount() {
		return elements.size();
	}

	/**
	 * Adds a element.
	 * 
	 * @param element the to add.
	 */
	public void appendElement(final T element) {
		final int index = elements.size();
		elements.add(element);
		if (checkContext()) {
			element.setContext(getContext());
			fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_ADDED, getContext().getTimestamp(), element, index));
		}
	}

	/**
	 * Inserts a element.
	 * 
	 * @param index the index.
	 * @param element the to add.
	 */
	public void insertElementAfter(final int index, final T element) {
		if ((index < 0) || (index > elements.size() - 1)) {
			throw new IllegalArgumentException("index out of bounds");
		}
		if (index < elements.size() - 1) {
			elements.add(index + 1, element);
		}
		else {
			elements.add(element);
		}
		if (checkContext()) {
			element.setContext(getContext());
			fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_INSERTED_AFTER, getContext().getTimestamp(), element, index));
		}
	}

	/**
	 * Inserts a element.
	 * 
	 * @param index the index.
	 * @param element the to add.
	 */
	public void insertElementBefore(final int index, final T element) {
		if ((index < 0) || (index > elements.size() - 1)) {
			throw new IllegalArgumentException("index out of bounds");
		}
		elements.add(index, element);
		if (checkContext()) {
			element.setContext(getContext());
			fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_INSERTED_BEFORE, getContext().getTimestamp(), element, index));
		}
	}

	/**
	 * Removes a element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeElement(final int index) {
		final T element = elements.remove(index);
		if (checkContext()) {
			fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_REMOVED, getContext().getTimestamp(), element, index));
		}
	}

	/**
	 * Removes a element.
	 * 
	 * @param element the to remove.
	 */
	public void removeElement(final T element) {
		final int index = elements.indexOf(element);
		elements.remove(index);
		if (checkContext()) {
			fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_REMOVED, getContext().getTimestamp(), element, index));
		}
	}

	/**
	 * Inserts a element.
	 * 
	 * @param index the index.
	 * @param element the to add.
	 */
	public void moveElementUp(final int index) {
		if ((index < 0) || (index > elements.size() - 1)) {
			throw new IllegalArgumentException("index out of bounds");
		}
		if (index > 0) {
			final T element = elements.remove(index);
			elements.add(index - 1, element);
			if (checkContext()) {
				element.setContext(getContext());
				fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_MOVED_UP, getContext().getTimestamp(), element, index));
			}
		}
	}

	/**
	 * Inserts a element.
	 * 
	 * @param index the index.
	 * @param element the to add.
	 */
	public void moveElementDown(final int index) {
		if ((index < 0) || (index > elements.size() - 1)) {
			throw new IllegalArgumentException("index out of bounds");
		}
		if (index < elements.size() - 1) {
			final T element = elements.remove(index);
			elements.add(index + 1, element);
			if (checkContext()) {
				element.setContext(getContext());
				fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_MOVED_DOWN, getContext().getTimestamp(), element, index));
			}
		}
	}

	/**
	 * @param index
	 * @param element
	 */
	public void setElement(final int index, final T element) {
		elements.set(index, element);
		if (checkContext()) {
			element.setContext(getContext());
			fireConfigChanged(new ElementChangeEvent(ListConfigElement.ELEMENT_CHANGED, getContext().getTimestamp(), element, index));
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ListConfigElement<T> clone() {
		final ListConfigElement<T> element = new ListConfigElement<T>(getClassId());
		for (int i = 0; i < getElementCount(); i++) {
			element.appendElement((T) getElement(i).clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.runtime.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		for (int i = 0; i < getElementCount(); i++) {
			getElement(i).setContext(getContext());
		}
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final ListConfigElement other = (ListConfigElement) obj;
		if (elements == null) {
			if (other.elements != null) {
				return false;
			}
		}
		else if (!elements.equals(other.elements)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		if (elements != null) {
			elements.clear();
			elements = null;
		}
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void copyFrom(ConfigElement source) {
		ListConfigElement<T> element = (ListConfigElement<T>) source;
		while (element.getElementCount() < getElementCount()) {
			removeElement(0);
		}
		for (int i = 0; i < getElementCount(); i++) {
			elements.get(i).copyFrom(element.getElement(i));
		}
		for (int i = getElementCount(); i < element.getElementCount(); i++) {
			appendElement((T) element.getElement(i).clone());
		}
	}
}
