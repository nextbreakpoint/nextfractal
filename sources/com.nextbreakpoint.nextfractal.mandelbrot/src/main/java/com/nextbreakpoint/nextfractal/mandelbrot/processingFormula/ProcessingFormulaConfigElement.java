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
package com.nextbreakpoint.nextfractal.mandelbrot.processingFormula;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ReferenceElement;

/**
 * @author Andrea Medeghini
 */
public class ProcessingFormulaConfigElement extends AbstractConfigElement implements ReferenceElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "ProcessingFormula";
	private final ExtensionReferenceElement extensionElement = new ExtensionReferenceElement();

	/**
	 * Constructs a new element.
	 */
	public ProcessingFormulaConfigElement() {
		super(ProcessingFormulaConfigElement.CLASS_ID);
	}

	/**
	 * @return
	 */
	@Override
	public ProcessingFormulaConfigElement clone() {
		final ProcessingFormulaConfigElement element = new ProcessingFormulaConfigElement();
		if (getReference() != null) {
			element.setReference(getReference().clone());
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		ProcessingFormulaConfigElement element = (ProcessingFormulaConfigElement) source;
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
	}

	/**
	 * @return
	 */
	@Override
	public ExtensionReference getReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	@Override
	public void setReference(final ExtensionReference reference) {
		extensionElement.setReference(reference);
	}

	/**
	 * @return
	 */
	public ExtensionReferenceElement getExtensionElement() {
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
		final ProcessingFormulaConfigElement other = (ProcessingFormulaConfigElement) obj;
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
