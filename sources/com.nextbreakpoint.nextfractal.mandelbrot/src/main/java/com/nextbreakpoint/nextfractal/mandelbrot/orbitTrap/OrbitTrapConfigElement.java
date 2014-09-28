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
package com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap;

import com.nextbreakpoint.nextfractal.core.common.ComplexElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class OrbitTrapConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "OrbitTrap";
	private final ConfigurableExtensionReferenceElement<OrbitTrapExtensionConfig> extensionElement = new ConfigurableExtensionReferenceElement<OrbitTrapExtensionConfig>();
	private final ComplexElement centerElement = new ComplexElement(new DoubleVector2D(0, 0));

	/**
	 * Constructs a new element.
	 */
	public OrbitTrapConfigElement() {
		super(OrbitTrapConfigElement.CLASS_ID);
	}

	/**
	 * @return
	 */
	@Override
	public OrbitTrapConfigElement clone() {
		final OrbitTrapConfigElement element = new OrbitTrapConfigElement();
		element.setCenter(getCenter());
		if (getReference() != null) {
			element.setReference(getReference().clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		OrbitTrapConfigElement element = (OrbitTrapConfigElement) source;
		setCenter(element.getCenter());
		if (element.getReference() != null) {
			setReference(element.getReference().clone());
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		extensionElement.setContext(context);
		centerElement.setContext(context);
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReference<OrbitTrapExtensionConfig> getReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	public void setReference(final ConfigurableExtensionReference<OrbitTrapExtensionConfig> reference) {
		extensionElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<OrbitTrapExtensionConfig> getExtensionElement() {
		return extensionElement;
	}

	/**
	 * @return the center
	 */
	public DoubleVector2D getCenter() {
		return centerElement.getValue();
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(final DoubleVector2D center) {
		centerElement.setValue(center);
	}

	/**
	 * @return
	 */
	public ComplexElement getCenterElement() {
		return centerElement;
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
		final OrbitTrapConfigElement other = (OrbitTrapConfigElement) obj;
		if (extensionElement == null) {
			if (other.extensionElement != null) {
				return false;
			}
		}
		else if (!extensionElement.equals(other.extensionElement)) {
			return false;
		}
		if (centerElement == null) {
			if (other.centerElement != null) {
				return false;
			}
		}
		else if (!centerElement.equals(other.centerElement)) {
			return false;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		extensionElement.dispose();
		centerElement.dispose();
		super.dispose();
	}
}
