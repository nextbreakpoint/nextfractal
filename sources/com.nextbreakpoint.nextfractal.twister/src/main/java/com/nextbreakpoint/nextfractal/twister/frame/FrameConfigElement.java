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
package com.nextbreakpoint.nextfractal.twister.frame;

import com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigContext;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;

/**
 * @author Andrea Medeghini
 */
public class FrameConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "Frame";
	private final ListConfigElement<GroupLayerConfigElement> layerListElement = new ListConfigElement<GroupLayerConfigElement>("GroupLayerListElement", 1);
	private final ListConfigElement<FrameFilterConfigElement> filterListElement = new ListConfigElement<FrameFilterConfigElement>("FrameFilterListElement", 0);

	/**
	 * 
	 */
	public FrameConfigElement() {
		super(FrameConfigElement.CLASS_ID);
	}

	/**
	 * Returns a layer element.
	 * 
	 * @param index the layer index.
	 * @return the layer.
	 */
	public GroupLayerConfigElement getLayerConfigElement(final int index) {
		return layerListElement.getElement(index);
	}

	/**
	 * Returns a layer element index.
	 * 
	 * @param layerElement the layer element.
	 * @return the index.
	 */
	public int indexOfLayerConfigElement(final GroupLayerConfigElement layerElement) {
		return layerListElement.indexOfElement(layerElement);
	}

	/**
	 * Returns the number of layer elements.
	 * 
	 * @return the number of layer elements.
	 */
	public int getLayerConfigElementCount() {
		return layerListElement.getElementCount();
	}

	/**
	 * Adds a layer element.
	 * 
	 * @param layerElement the layer to add.
	 */
	public void appendLayerConfigElement(final GroupLayerConfigElement layerElement) {
		layerListElement.appendElement(layerElement);
	}

	/**
	 * Adds a layer element.
	 * 
	 * @param index the index.
	 * @param layerElement the layer to add.
	 */
	public void insertLayerConfigElementAfter(final int index, final GroupLayerConfigElement layerElement) {
		layerListElement.insertElementAfter(index, layerElement);
	}

	/**
	 * Adds a layer element.
	 * 
	 * @param index the index.
	 * @param layerElement the layer to add.
	 */
	public void insertLayerConfigElementBefore(final int index, final GroupLayerConfigElement layerElement) {
		layerListElement.insertElementBefore(index, layerElement);
	}

	/**
	 * Removes a layer element.
	 * 
	 * @param index the element index to remove.
	 */
	public void removeLayerConfigElement(final int index) {
		layerListElement.removeElement(index);
	}

	/**
	 * Removes a layer element.
	 * 
	 * @param layerElement the layer to remove.
	 */
	public void removeLayerConfigElement(final GroupLayerConfigElement layerElement) {
		layerListElement.removeElement(layerElement);
	}

	/**
	 * Returns a filter element.
	 * 
	 * @param index the filter index.
	 * @return the filter.
	 */
	public FrameFilterConfigElement getFilterConfigElement(final int index) {
		return filterListElement.getElement(index);
	}

	/**
	 * Returns a filter element index.
	 * 
	 * @param filterElement the filter element.
	 * @return the filter index.
	 */
	public int indexOfFilterConfigElement(final FrameFilterConfigElement filterElement) {
		return filterListElement.indexOfElement(filterElement);
	}

	/**
	 * Retruns the number of filter elements.
	 * 
	 * @return the number of filter elements.
	 */
	public int getFilterConfigElementCount() {
		return filterListElement.getElementCount();
	}

	/**
	 * Adds a filter element.
	 * 
	 * @param filterElement the layer to add.
	 */
	public void appendFilterConfigElement(final FrameFilterConfigElement filterElement) {
		filterListElement.appendElement(filterElement);
	}

	/**
	 * Adds a filter element.
	 * 
	 * @param index the index.
	 * @param filterElement the filter to add.
	 */
	public void insertFilterConfigElementAfter(final int index, final FrameFilterConfigElement filterElement) {
		filterListElement.insertElementAfter(index, filterElement);
	}

	/**
	 * Adds a filter element.
	 * 
	 * @param index the index.
	 * @param filterElement the filter to add.
	 */
	public void insertFilterConfigElementBefore(final int index, final FrameFilterConfigElement filterElement) {
		filterListElement.insertElementBefore(index, filterElement);
	}

	/**
	 * Removes a filter element.
	 * 
	 * @param index the index to remove.
	 * @return the filter element.
	 */
	public void removeFilterConfigElement(final int index) {
		filterListElement.removeElement(index);
	}

	/**
	 * Removes a filter element.
	 * 
	 * @param filterElement the filter to remove.
	 */
	public void removeFilterConfigElement(final FrameFilterConfigElement filterElement) {
		filterListElement.removeElement(filterElement);
	}

	/**
	 * @param index
	 */
	public void moveUpLayerConfigElement(final int index) {
		layerListElement.moveElementUp(index);
	}

	/**
	 * @param index
	 */
	public void moveDownLayerConfigElement(final int index) {
		layerListElement.moveElementDown(index);
	}

	/**
	 * @param index
	 */
	public void moveUpFilterConfigElement(final int index) {
		filterListElement.moveElementUp(index);
	}

	/**
	 * @param index
	 */
	public void moveDownFilterConfigElement(final int index) {
		filterListElement.moveElementDown(index);
	}

	/**
	 * @return
	 */
	@Override
	public FrameConfigElement clone() {
		final FrameConfigElement element = new FrameConfigElement();
		for (int i = 0; i < getLayerListElement().getElementCount(); i++) {
			element.appendLayerConfigElement(getLayerListElement().getElement(i).clone());
		}
		for (int i = 0; i < getFilterListElement().getElementCount(); i++) {
			element.appendFilterConfigElement(getFilterListElement().getElement(i).clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		FrameConfigElement element = (FrameConfigElement) source;
		getLayerListElement().copyFrom(element.getLayerListElement());
		getFilterListElement().copyFrom(element.getFilterListElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.runtime.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		filterListElement.setContext(getContext());
		layerListElement.setContext(getContext());
	}

	/**
	 * @return the filterListElement
	 */
	public ListConfigElement<FrameFilterConfigElement> getFilterListElement() {
		return filterListElement;
	}

	/**
	 * @return the layerListElement
	 */
	public ListConfigElement<GroupLayerConfigElement> getLayerListElement() {
		return layerListElement;
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
		final FrameConfigElement other = (FrameConfigElement) obj;
		if (filterListElement == null) {
			if (other.filterListElement != null) {
				return false;
			}
		}
		else if (!filterListElement.equals(other.filterListElement)) {
			return false;
		}
		if (layerListElement == null) {
			if (other.layerListElement != null) {
				return false;
			}
		}
		else if (!layerListElement.equals(other.layerListElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		layerListElement.dispose();
		filterListElement.dispose();
		super.dispose();
	}
}
