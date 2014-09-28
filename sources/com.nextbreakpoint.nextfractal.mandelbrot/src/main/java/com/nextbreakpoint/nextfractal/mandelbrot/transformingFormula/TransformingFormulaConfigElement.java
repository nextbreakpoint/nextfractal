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
package com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.extension.TransformingFormulaExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class TransformingFormulaConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "TransformingFormula";
	private final ConfigurableExtensionReferenceElement<TransformingFormulaExtensionConfig> extensionElement = new ConfigurableExtensionReferenceElement<TransformingFormulaExtensionConfig>();

	/**
	 * Constructs a new element.
	 */
	public TransformingFormulaConfigElement() {
		super(TransformingFormulaConfigElement.CLASS_ID);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		extensionElement.setContext(context);
	}

	/**
	 * @return
	 */
	@Override
	public TransformingFormulaConfigElement clone() {
		final TransformingFormulaConfigElement element = new TransformingFormulaConfigElement();
		if (getReference() != null) {
			element.setReference(getReference().clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		TransformingFormulaConfigElement element = (TransformingFormulaConfigElement) source;
		if (element.getReference() != null) {
			setReference(element.getReference().clone());
		}
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReference<TransformingFormulaExtensionConfig> getReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	public void setReference(final ConfigurableExtensionReference<TransformingFormulaExtensionConfig> reference) {
		extensionElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<TransformingFormulaExtensionConfig> getExtensionElement() {
		return extensionElement;
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
		final TransformingFormulaConfigElement other = (TransformingFormulaConfigElement) obj;
		if (extensionElement == null) {
			if (other.extensionElement != null) {
				return false;
			}
		}
		else if (!extensionElement.equals(other.extensionElement)) {
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
		super.dispose();
	}
}
