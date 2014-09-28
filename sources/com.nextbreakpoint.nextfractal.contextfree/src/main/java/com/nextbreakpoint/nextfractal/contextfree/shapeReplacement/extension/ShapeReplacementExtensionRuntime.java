/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.extension;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFBuilder;
import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFRule;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class ShapeReplacementExtensionRuntime<T extends ShapeReplacementExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param context
	 */
	public abstract void process(CFBuilder context, CFRule rule);
}
