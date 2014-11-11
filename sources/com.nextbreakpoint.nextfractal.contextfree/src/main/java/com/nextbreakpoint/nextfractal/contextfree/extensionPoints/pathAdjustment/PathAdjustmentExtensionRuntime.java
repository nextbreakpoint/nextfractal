/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.renderer.support.CFModification;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class PathAdjustmentExtensionRuntime<T extends PathAdjustmentExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param state
	 */
	public abstract void apply(CFModification mod);
}
