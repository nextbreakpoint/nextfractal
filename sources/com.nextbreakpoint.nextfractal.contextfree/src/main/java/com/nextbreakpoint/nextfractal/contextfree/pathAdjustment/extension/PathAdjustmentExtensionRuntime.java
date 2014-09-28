/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.extension;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class PathAdjustmentExtensionRuntime<T extends PathAdjustmentExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param state
	 */
	public abstract void apply(CFModification mod);
}
