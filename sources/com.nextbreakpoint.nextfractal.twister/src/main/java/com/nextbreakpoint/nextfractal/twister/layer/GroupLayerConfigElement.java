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

import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ListConfigElement;

/**
 * @author Andrea Medeghini
 */
public class GroupLayerConfigElement extends AbstractLayerConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "GroupLayer";
	private final ListConfigElement<ImageLayerConfigElement> layerListElement = new ListConfigElement<ImageLayerConfigElement>("ImageLayerListElement");
	private final StringElement labelElement = new StringElement("New Group");

	/**
	 * 
	 */
	public GroupLayerConfigElement() {
		super(GroupLayerConfigElement.CLASS_ID);
	}

	/**
	 * Returns a layer element.
	 * 
	 * @param index the layer index.
	 * @return the layer.
	 */
	public ImageLayerConfigElement getLayerConfigElement(final int index) {
		return layerListElement.getElement(index);
	}

	/**
	 * Returns a layer element index.
	 * 
	 * @param layerElement the layer element.
	 * @return the index.
	 */
	public int indexOfLayerConfigElement(final ImageLayerConfigElement layerElement) {
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
	public void appendLayerConfigElement(final ImageLayerConfigElement layerElement) {
		layerListElement.appendElement(layerElement);
	}

	/**
	 * Adds a layer element.
	 * 
	 * @param index the index.
	 * @param layerElement the layer to add.
	 */
	public void insertLayerConfigElementAfter(final int index, final ImageLayerConfigElement layerElement) {
		layerListElement.insertElementAfter(index, layerElement);
	}

	/**
	 * Adds a layer element.
	 * 
	 * @param index the index.
	 * @param layerElement the layer to add.
	 */
	public void insertLayerConfigElementBefore(final int index, final ImageLayerConfigElement layerElement) {
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
	public void removeLayerConfigElement(final ImageLayerConfigElement layerElement) {
		layerListElement.removeElement(layerElement);
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
	 * @return
	 */
	@Override
	public GroupLayerConfigElement clone() {
		final GroupLayerConfigElement element = new GroupLayerConfigElement();
		element.setLabel(getLabel());
		element.setLocked(isLocked());
		element.setVisible(isVisible());
		element.setOpacity(getOpacity());
		for (int i = 0; i < getLayerListElement().getElementCount(); i++) {
			element.appendLayerConfigElement(getLayerListElement().getElement(i).clone());
		}
		for (int i = 0; i < getFilterListElement().getElementCount(); i++) {
			element.appendFilterConfigElement(getFilterListElement().getElement(i).clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		GroupLayerConfigElement element = (GroupLayerConfigElement) source;
		setLabel(element.getLabel());
		setLocked(element.isLocked());
		setVisible(element.isVisible());
		setOpacity(element.getOpacity());
		getLayerListElement().copyFrom(element.getLayerListElement());
		getFilterListElement().copyFrom(element.getFilterListElement());
	}

	/**
	 * @param label
	 */
	public void setLabel(final String label) {
		labelElement.setValue(label);
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return labelElement.getValue();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		labelElement.setContext(getContext());
		layerListElement.setContext(getContext());
	}

	/**
	 * @return the layerListElement
	 */
	public ListConfigElement<ImageLayerConfigElement> getLayerListElement() {
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
		if (!super.equals(obj)) {
			return false;
		}
		final GroupLayerConfigElement other = (GroupLayerConfigElement) obj;
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
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		labelElement.dispose();
		layerListElement.dispose();
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.layer.LayerConfigElement#getLabelElement()
	 */
	public StringElement getLabelElement() {
		return labelElement;
	}
}
