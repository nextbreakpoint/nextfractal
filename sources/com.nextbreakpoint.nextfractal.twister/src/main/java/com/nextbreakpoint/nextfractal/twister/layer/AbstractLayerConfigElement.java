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
package com.nextbreakpoint.nextfractal.twister.layer;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractLayerConfigElement extends AbstractConfigElement implements LayerConfigElement {
	private static final long serialVersionUID = 1L;
	private final PercentageElement opacityElement = new PercentageElement(100);
	private final BooleanElement lockedElement = new BooleanElement(Boolean.FALSE);
	private final BooleanElement visibleElement = new BooleanElement(Boolean.TRUE);
	private final ListConfigElement<LayerFilterConfigElement> filterListElement = new ListConfigElement<LayerFilterConfigElement>("LayerFilterListElement");

	/**
	 * @param classId
	 */
	protected AbstractLayerConfigElement(final String classId) {
		super(classId);
	}

	/**
	 * Returns the layer opacity.
	 * 
	 * @return the layer opacity.
	 */
	public Integer getOpacity() {
		return opacityElement.getValue();
	}

	/**
	 * Sets the layer opacity.
	 * 
	 * @param opacity the layer opacity to set.
	 */
	public void setOpacity(final Integer opacity) {
		opacityElement.setValue(opacity);
	}

	/**
	 * @param locked
	 */
	public void setLocked(final Boolean locked) {
		lockedElement.setValue(locked);
	}

	/**
	 * @return true if locked.
	 */
	public Boolean isLocked() {
		return lockedElement.getValue();
	}

	/**
	 * @param visible
	 */
	public void setVisible(final Boolean visible) {
		visibleElement.setValue(visible);
	}

	/**
	 * @return true if visible.
	 */
	public Boolean isVisible() {
		return visibleElement.getValue();
	}

	/**
	 * Returns a filter element.
	 * 
	 * @param index the filter index.
	 * @return the filter.
	 */
	public LayerFilterConfigElement getFilterConfigElement(final int index) {
		return filterListElement.getElement(index);
	}

	/**
	 * Returns a filter element index.
	 * 
	 * @param filterElement the filter element.
	 * @return the filter index.
	 */
	public int indexOfFilterConfigElement(final LayerFilterConfigElement filterElement) {
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
	public void appendFilterConfigElement(final LayerFilterConfigElement filterElement) {
		filterListElement.appendElement(filterElement);
	}

	/**
	 * Adds a filter element.
	 * 
	 * @param index the index.
	 * @param filterElement the filter to add.
	 */
	public void insertFilterConfigElementAfter(final int index, final LayerFilterConfigElement filterElement) {
		filterListElement.insertElementAfter(index, filterElement);
	}

	/**
	 * Adds a filter element.
	 * 
	 * @param index the index.
	 * @param filterElement the filter to add.
	 */
	public void insertFilterConfigElementBefore(final int index, final LayerFilterConfigElement filterElement) {
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
	public void removeFilterConfigElement(final LayerFilterConfigElement filterElement) {
		filterListElement.removeElement(filterElement);
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
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		opacityElement.setContext(context);
		lockedElement.setContext(context);
		visibleElement.setContext(context);
		filterListElement.setContext(context);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.layer.LayerConfigElement#getOpacityElement()
	 */
	public PercentageElement getOpacityElement() {
		return opacityElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.layer.LayerConfigElement#getLockedElement()
	 */
	public BooleanElement getLockedElement() {
		return lockedElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.layer.LayerConfigElement#getVisibleElement()
	 */
	public BooleanElement getVisibleElement() {
		return visibleElement;
	}

	/**
	 * @return the filterListElement
	 */
	public ListConfigElement<LayerFilterConfigElement> getFilterListElement() {
		return filterListElement;
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
		final AbstractLayerConfigElement other = (AbstractLayerConfigElement) obj;
		if (filterListElement == null) {
			if (other.filterListElement != null) {
				return false;
			}
		}
		else if (!filterListElement.equals(other.filterListElement)) {
			return false;
		}
		if (lockedElement == null) {
			if (other.lockedElement != null) {
				return false;
			}
		}
		else if (!lockedElement.equals(other.lockedElement)) {
			return false;
		}
		if (opacityElement == null) {
			if (other.opacityElement != null) {
				return false;
			}
		}
		else if (!opacityElement.equals(other.opacityElement)) {
			return false;
		}
		if (visibleElement == null) {
			if (other.visibleElement != null) {
				return false;
			}
		}
		else if (!visibleElement.equals(other.visibleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		lockedElement.dispose();
		opacityElement.dispose();
		visibleElement.dispose();
		filterListElement.dispose();
		super.dispose();
	}
}
