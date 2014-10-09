/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeReplacement;

import com.nextbreakpoint.nextfractal.contextfree.CFDGBuilder;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeReplacement.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class ShapeReplacementConfigElement extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "ShapeReplacement";
	private final ConfigurableExtensionReferenceElement<ShapeReplacementExtensionConfig> extensionElement = new ConfigurableExtensionReferenceElement<ShapeReplacementExtensionConfig>();

	/**
	 * Constructs a new element.
	 */
	public ShapeReplacementConfigElement() {
		super(ShapeReplacementConfigElement.CLASS_ID);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
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
	public ShapeReplacementConfigElement clone() {
		final ShapeReplacementConfigElement element = new ShapeReplacementConfigElement();
		if (getExtensionReference() != null) {
			element.setExtensionReference(getExtensionReference().clone());
		}
		return element;
	}
	
	/**
	 *
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		ShapeReplacementConfigElement shapeReplacementElement = (ShapeReplacementConfigElement) source;
		if (shapeReplacementElement.getExtensionReference() != null) {
			setExtensionReference(shapeReplacementElement.getExtensionReference().clone());
		}
	}

	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<ShapeReplacementExtensionConfig> getExtensionElement() {
		return extensionElement;
	}
	
	/**
	 * @return
	 */
	public ConfigurableExtensionReference<ShapeReplacementExtensionConfig> getExtensionReference() {
		return extensionElement.getReference();
	}

	/**
	 * @param reference
	 */
	public void setExtensionReference(final ConfigurableExtensionReference<ShapeReplacementExtensionConfig> reference) {
		extensionElement.setReference(reference);
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
		final ShapeReplacementConfigElement other = (ShapeReplacementConfigElement) obj;
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

	public void toCFDG(CFDGBuilder builder) {
		if (extensionElement.getReference() != null) {
			if (extensionElement.getReference().getExtensionConfig() != null) {
				extensionElement.getReference().getExtensionConfig().toCFDG(builder);
			}
		}
	}
}
