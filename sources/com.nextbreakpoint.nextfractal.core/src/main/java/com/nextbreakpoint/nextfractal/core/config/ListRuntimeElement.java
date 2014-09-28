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
package com.nextbreakpoint.nextfractal.core.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public class ListRuntimeElement<T extends RuntimeElement> extends RuntimeElement {
	private List<T> elements;

	/**
	 * 
	 */
	public ListRuntimeElement() {
		this(1);
	}

	/**
	 * 
	 */
	public ListRuntimeElement(final int size) {
		elements = new ArrayList<T>(size);
	}

	/**
	 * Returns a element.
	 * 
	 * @param index the index.
	 * @return the element.
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
		elements.add(element);
		fireChanged();
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
		fireChanged();
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
		fireChanged();
	}

	/**
	 * Removes a element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeElement(final int index) {
		elements.remove(index);
		fireChanged();
	}

	/**
	 * Removes a element.
	 * 
	 * @param element the to remove.
	 */
	public void removeElement(final T element) {
		final int index = elements.indexOf(element);
		elements.remove(index);
		fireChanged();
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
			fireChanged();
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
			fireChanged();
		}
	}

	/**
	 * @param index
	 * @param element
	 */
	public void setElement(final int index, final T element) {
		elements.set(index, element);
		fireChanged();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean isElementChanged = false;
		if (elements != null) {
			for (final T element : elements) {
				if (element.isChanged()) {
					isElementChanged = true;
				}
			}
		}
		return super.isChanged() || isElementChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if (elements != null) {
			for (final T element : elements) {
				element.dispose();
			}
			elements.clear();
			elements = null;
		}
		super.dispose();
	}
}
