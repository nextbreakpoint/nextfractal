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
package com.nextbreakpoint.nextfractal.twister.layer;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.elements.StringElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ListConfigElement;
import com.nextbreakpoint.nextfractal.twister.elements.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;

/**
 * @author Andrea Medeghini
 */
public interface LayerConfigElement extends ConfigElement {
	/**
	 * Returns the layer opacity.
	 * 
	 * @return the layer opacity.
	 */
	public Integer getOpacity();

	/**
	 * Sets the layer opacity.
	 * 
	 * @param opacity the layer opacity to set.
	 */
	public void setOpacity(Integer opacity);

	/**
	 * @param locked
	 */
	public void setLocked(Boolean locked);

	/**
	 * @return true if locked.
	 */
	public Boolean isLocked();

	/**
	 * @param visible
	 */
	public void setVisible(Boolean visible);

	/**
	 * @return true if visible.
	 */
	public Boolean isVisible();

	/**
	 * Returns a filter element.
	 * 
	 * @param index the filter index.
	 * @return the filter.
	 */
	public LayerFilterConfigElement getFilterConfigElement(int index);

	/**
	 * Returns a filter element index.
	 * 
	 * @param filterElement the filter element.
	 * @return the filter index.
	 */
	public int indexOfFilterConfigElement(LayerFilterConfigElement filterElement);

	/**
	 * Retruns the number of filter elements.
	 * 
	 * @return the number of filter elements.
	 */
	public int getFilterConfigElementCount();

	/**
	 * Adds a filter element.
	 * 
	 * @param filterElement the layer to add.
	 */
	public void appendFilterConfigElement(LayerFilterConfigElement filterElement);

	/**
	 * Adds a filter element.
	 * 
	 * @param index the index.
	 * @param filterElement the filter to add.
	 */
	public void insertFilterConfigElementAfter(int index, LayerFilterConfigElement filterElement);

	/**
	 * Adds a filter element.
	 * 
	 * @param index the index.
	 * @param filterElement the filter to add.
	 */
	public void insertFilterConfigElementBefore(int index, LayerFilterConfigElement filterElement);

	/**
	 * Removes a filter element.
	 * 
	 * @param index the index to remove.
	 * @return the filter element.
	 */
	public void removeFilterConfigElement(int index);

	/**
	 * Removes a filter element.
	 * 
	 * @param filterElement the filter to remove.
	 */
	public void removeFilterConfigElement(LayerFilterConfigElement filterElement);

	/**
	 * @return the opacityElement
	 */
	public PercentageElement getOpacityElement();

	/**
	 * @return the lockedElement
	 */
	public BooleanElement getLockedElement();

	/**
	 * @return the visibleElement
	 */
	public BooleanElement getVisibleElement();

	/**
	 * @return the labelElement
	 */
	public StringElement getLabelElement();

	/**
	 * @return
	 */
	public ListConfigElement<LayerFilterConfigElement> getFilterListElement();
}
