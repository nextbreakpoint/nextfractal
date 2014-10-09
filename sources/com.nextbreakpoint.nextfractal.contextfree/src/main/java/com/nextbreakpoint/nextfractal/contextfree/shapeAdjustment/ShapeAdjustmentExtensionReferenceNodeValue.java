/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentExtensionReferenceNodeValue extends ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig>> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public ShapeAdjustmentExtensionReferenceNodeValue(final ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> value) {
		super(value);
	}
}
