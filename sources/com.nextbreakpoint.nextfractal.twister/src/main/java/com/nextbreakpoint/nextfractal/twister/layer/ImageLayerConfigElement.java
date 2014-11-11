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

import com.nextbreakpoint.nextfractal.core.elements.StringElement;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigContext;
import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.SingleConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;

/**
 * @author Andrea Medeghini
 */
public class ImageLayerConfigElement extends AbstractLayerConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "ImageLayer";
	private final SingleConfigElement<ImageConfigElement> imageSingleElement = new SingleConfigElement<ImageConfigElement>("ImageSingleElement");
	private final StringElement labelElement = new StringElement("New Layer");

	/**
	 * 
	 */
	public ImageLayerConfigElement() {
		super(ImageLayerConfigElement.CLASS_ID);
	}

	/**
	 * @return the image.
	 */
	public ImageConfigElement getImageConfigElement() {
		return imageSingleElement.getValue();
	}

	/**
	 * @param imageElement
	 */
	public void setImageConfigElement(final ImageConfigElement imageElement) {
		imageSingleElement.setValue(imageElement);
	}

	/**
	 * @return
	 */
	@Override
	public ImageLayerConfigElement clone() {
		final ImageLayerConfigElement element = new ImageLayerConfigElement();
		element.setLabel(getLabel());
		element.setLocked(isLocked());
		element.setVisible(isVisible());
		element.setOpacity(getOpacity());
		element.setImageConfigElement(getImageConfigElement().clone());
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
		ImageLayerConfigElement element = (ImageLayerConfigElement) source;
		setLabel(element.getLabel());
		setLocked(element.isLocked());
		setVisible(element.isVisible());
		setOpacity(element.getOpacity());
		getFilterListElement().copyFrom(element.getFilterListElement());
		getImageSingleElement().copyFrom(element.getImageSingleElement());
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
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.runtime.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		labelElement.setContext(getContext());
		imageSingleElement.setContext(getContext());
	}

	/**
	 * @return the imageSingleElement
	 */
	public SingleConfigElement<ImageConfigElement> getImageSingleElement() {
		return imageSingleElement;
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
		final ImageLayerConfigElement other = (ImageLayerConfigElement) obj;
		if (imageSingleElement == null) {
			if (other.imageSingleElement != null) {
				return false;
			}
		}
		else if (!imageSingleElement.equals(other.imageSingleElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		imageSingleElement.dispose();
		labelElement.dispose();
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.layer.LayerConfigElement#getLabelElement()
	 */
	@Override
	public StringElement getLabelElement() {
		return labelElement;
	}
}
