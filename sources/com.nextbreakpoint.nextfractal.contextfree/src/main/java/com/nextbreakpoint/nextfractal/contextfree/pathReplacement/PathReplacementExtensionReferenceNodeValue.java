/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class PathReplacementExtensionReferenceNodeValue extends ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<PathReplacementExtensionConfig>> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public PathReplacementExtensionReferenceNodeValue(final ConfigurableExtensionReference<PathReplacementExtensionConfig> value) {
		super(value);
	}
}
