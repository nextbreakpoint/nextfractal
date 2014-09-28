/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.figure;

import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNodeValue;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;

/**
 * @author Andrea Medeghini
 */
public class FigureExtensionReferenceNodeValue extends ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<FigureExtensionConfig>> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param value
	 */
	public FigureExtensionReferenceNodeValue(final ConfigurableExtensionReference<FigureExtensionConfig> value) {
		super(value);
	}
}
