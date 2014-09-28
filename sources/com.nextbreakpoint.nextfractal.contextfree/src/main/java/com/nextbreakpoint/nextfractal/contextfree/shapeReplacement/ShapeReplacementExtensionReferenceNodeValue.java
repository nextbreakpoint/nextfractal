/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeReplacement;

import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class ShapeReplacementExtensionReferenceNodeValue extends ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<ShapeReplacementExtensionConfig>> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ShapeReplacementExtensionReferenceNodeValue(final ConfigurableExtensionReference<ShapeReplacementExtensionConfig> value) {
		super(value);
	}
}
