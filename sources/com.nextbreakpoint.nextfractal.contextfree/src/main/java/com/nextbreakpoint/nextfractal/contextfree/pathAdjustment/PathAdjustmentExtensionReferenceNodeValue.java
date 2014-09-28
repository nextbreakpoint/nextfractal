/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.extension.PathAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class PathAdjustmentExtensionReferenceNodeValue extends ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<PathAdjustmentExtensionConfig>> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public PathAdjustmentExtensionReferenceNodeValue(final ConfigurableExtensionReference<PathAdjustmentExtensionConfig> value) {
		super(value);
	}
}
